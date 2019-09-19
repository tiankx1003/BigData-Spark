package com.tian.review.app

import com.tian.review.bean.{CategoryCountInfo, CategorySession, UserVisitAction}
import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkContext}

import scala.collection.mutable

/**
 * 需求2: 计算品类热度前10中的session活跃前10
 * 三种实现方式
 *
 * @author tian
 * @date 2019/9/19 11:38
 * @version 1.0.0
 */
object SessionTop10 {
    /**
     * 对于groupByKey分组后得到的sidCountIt直接toList
     * 会把数据全部都拉到executor的内存，造成OOM
     *
     * @param sc                上下文对象
     * @param userActionRDD     用户行为数据集
     * @param categoryTop10Info 热度品类前十
     */
    def sessionTop10_1(sc: SparkContext,
                       userActionRDD: RDD[UserVisitAction],
                       categoryTop10Info: List[CategoryCountInfo]): Unit = {
        userActionRDD
            .filter(action => //过滤出包含前十cid用户点击记录
                categoryTop10Info
                    .map(_.categoryId)
                    .contains(action.click_category_id.toString)
            )
            .map(action => ((action.click_category_id, action.session_id), 1)) //((cid, sid), 1)
            .reduceByKey(_ + _) //((cid, sid), count)
            .map { //(cid, (sid, count))
                case ((cid, sid), count) => (cid, (sid, count))
            }
            .groupByKey() //按照cid分组，(cid,sidCountIt)
            .map {
                case (cid, sidCountIt) => (cid, sidCountIt.toList.sortBy(-_._2).take(10)) //排序取前十
            }
            .foreach(println) //打印验证
    }

    /**
     * RDD的全局排序不会有内存移除问题
     * 存在shuffle过程，依赖磁盘的读写
     * 但是会启动过多的job
     *
     * @param sc                上下文对象
     * @param userActionRDD     用户行为数据集
     * @param categoryTop10Info 热度品类前十
     */
    def sessionTop10_2(sc: SparkContext,
                       userActionRDD: RDD[UserVisitAction],
                       categoryTop10Info: List[CategoryCountInfo]): Unit = {
        categoryTop10Info
            .map(_.categoryId)
            .foreach(cid => {
                userActionRDD
                    .filter(action => //过滤出包含前十cid用户点击记录
                        categoryTop10Info
                            .map(_.categoryId)
                            .contains(action.click_category_id.toString)
                    )
                    .map(action => ((action.click_category_id, action.session_id), 1)) //((cid, sid), 1)
                    .reduceByKey(_ + _) //((cid, sid), count)
                    .map { //(cid, (sid, count))
                        case ((cid, sid), count) => (cid, (sid, count))
                    }
                    .groupByKey() //按照cid分组，(cid,sidCountIt)
                    .filter(_._1.toString == cid)
                    .flatMap {
                        case (_, it) => it
                    }
                    .sortBy(_._2, ascending = false) //排序
                    .take(10) //取前十
                    .map { //封装成对象
                        case (sid, count) => CategorySession(cid, sid, count)
                    }
                    // 写入外部存储 jdbc,hbase,hive
                    .foreach(println) //打印测试
            })
    }

    /**
     * 使用TreeSet自动排序
     * 固定集合的长度始终为10，就不会发生OOM
     *
     * @param sc                上下文对象
     * @param userActionRDD     用户行为数据集
     * @param categoryTop10Info 热度品类前十
     */
    def sessionTop10_3(sc: SparkContext,
                       userActionRDD: RDD[UserVisitAction],
                       categoryTop10Info: List[CategoryCountInfo]): Unit = {
        userActionRDD
            .filter(action => //过滤出包含前十cid用户点击记录
                categoryTop10Info
                    .map(_.categoryId)
                    .contains(action.click_category_id.toString)
            )
            .map(action => ((action.click_category_id, action.session_id), 1)) //((cid, sid), 1)
            .reduceByKey(new MyPartitioner(categoryTop10Info.map(_.categoryId)), _ + _) //((cid, sid), count)
            .map { //(cid, (sid, count))
                case ((cid, sid), count) => CategorySession(cid.toString, sid, count)
            }
            .mapPartitions(it => {
                var treeSet: mutable.TreeSet[CategorySession] = new mutable.TreeSet[CategorySession]()
                it.foreach(cs => {
                    treeSet += cs
                    if (treeSet.size > 10)
                        treeSet = treeSet.take(10)
                })
                treeSet.toIterator
            })
            .foreach(println) //输出验证
    }
}

/**
 * 自定分区器
 * 每个分区对应一个cid
 *
 * @param categoryTop10 品类top10的cid
 */
class MyPartitioner(categoryTop10: List[String]) extends Partitioner {
    var cidIndexList: Map[String, Int] = categoryTop10.zipWithIndex.toMap

    override def numPartitions: Int = categoryTop10.size

    override def getPartition(key: Any): Int = {
        key match {
            case ((cid: Long, _)) => cidIndexList(cid.toString)
        }
    }
}


