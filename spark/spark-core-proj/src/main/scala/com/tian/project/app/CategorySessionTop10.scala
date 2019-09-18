package com.tian.project.app

import com.tian.project.bean.{CategoryCountInfo, CategorySession, UserVisitAction}
import org.apache.spark.{Partitioner, SparkContext}
import org.apache.spark.rdd.RDD

import scala.collection.mutable

/**
 * @author tian
 * @date 2019/9/18 15:12
 * @version 1.0.0
 */
/*
cid, sidCountIt.toList.sortBy(-_._2).take(10)
toList会把数据全部拉到内存(executor)，可能造成OOM
rdd的全局排序没有内存溢出问题，shuffle，依赖磁盘
全局排序虽然没有内存移除的问题，但是会启动过多的job
 */
object CategorySessionTop10 {
    def statCategoryTop10Session1(sc: SparkContext,
                                  userVisitActionRDD: RDD[UserVisitAction],
                                  top10CategoryCountInfo: List[CategoryCountInfo]): Unit = {
        val result: RDD[(Long, Iterable[(String, Int)])] = userVisitActionRDD
            .filter(action => { // 把包含top10cid的用户点击记录过滤出来
                top10CategoryCountInfo.map(_.categoryId).contains(action.click_category_id.toString)
            })
            .map(action => ((action.click_category_id, action.session_id), 1))
            .reduceByKey(_ + _)
            .map {
                case ((cid, sid), count) => (cid, (sid, count))
            }
            .groupByKey()
            .map { //toList会把数据全部拉到内存(executor)，可能造成OOM
                case (cid, sidCountIt) => (cid, sidCountIt.toList.sortBy(-_._2).take(10))
            }
        result.foreach(println) //打印测试
    }

    def statCategoryTop10Session2(sc: SparkContext,
                                  userVisitActionRDD: RDD[UserVisitAction],
                                  top10CategoryCountInfo: List[CategoryCountInfo]): Unit = {
        val itRDD: RDD[(Long, Iterable[(String, Int)])] = userVisitActionRDD
            .filter(action => { // 把包含top10cid的用户点击记录过滤出来
                top10CategoryCountInfo.map(_.categoryId).contains(action.click_category_id.toString)
            })
            .map(action => ((action.click_category_id, action.session_id), 1))
            .reduceByKey(_ + _)
            .map {
                case ((cid, sid), count) => (cid, (sid, count))
            }
            .groupByKey()
        top10CategoryCountInfo.map(_.categoryId).foreach(cid => {
            val onlyCidRDD: RDD[(Long, Iterable[(String, Int)])] = itRDD.filter(_._1.toString == cid)
            //onlyCidRDD.collect.foreach(println) //打印测试
            val result: Array[CategorySession] = onlyCidRDD.flatMap { //添加Iterable[(String, Int)]
                case (_, it) => it
            }
                .sortBy(_._2, ascending = false)
                .take(10)
                .map {
                    case (sid, count) => CategorySession(cid, sid, count)
                }
            // 写入外部存储 jdbc,hbase,hive
            result.foreach(println) //打印测试
        })
    }

    //TODO 使用Set自动去重，视频
    def statCategoryTop10Session3(sc: SparkContext,
                                  userVisitActionRDD: RDD[UserVisitAction],
                                  top10CategoryCountInfo: List[CategoryCountInfo]): Unit = {
        val csRDD: RDD[CategorySession] = userVisitActionRDD
            .filter(action => { // 把包含top10cid的用户点击记录过滤出来
                top10CategoryCountInfo.map(_.categoryId).contains(action.click_category_id.toString)
            })
            .map(action => ((action.click_category_id, action.session_id), 1))
            .reduceByKey(new MyPartitioner(top10CategoryCountInfo.map(_.categoryId)), _ + _)
            .map {
                case ((cid, sid), count) => CategorySession(cid.toString, sid, count)
            }
        val resultRDD = csRDD.mapPartitions(it => {
            var treeSet = new mutable.TreeSet[CategorySession]
            it.foreach(cs => {
                treeSet += cs
                if (treeSet.size > 10) {
                    //treeSet.remove(treeSet.last)
                    treeSet = treeSet.take(10)
                }
            })
            treeSet.toIterator
        })
        resultRDD.collect.foreach(println) //打印测试
    }
}

class MyPartitioner(categoryIdTop10: List[String]) extends Partitioner {
    private val cidIndexList: Map[String, Int] = categoryIdTop10.zipWithIndex.toMap

    override def numPartitions: Int = categoryIdTop10.size

    override def getPartition(key: Any): Int = {
        key match {
            case (cid: Long, _) => cidIndexList(cid.toString)
        }
    }
}