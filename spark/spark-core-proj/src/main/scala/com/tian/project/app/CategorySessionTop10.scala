package com.tian.project.app

import com.tian.project.bean.{CategoryCountInfo, CategorySession, UserVisitAction}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

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
    def statCategoryTop10Session(sc: SparkContext,
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
        /*.map { //toList会把数据全部拉到内存(executor)，可能造成OOM
            case (cid, sidCountIt) => (cid, sidCountIt.toList.sortBy(-_._2).take(10))
        }*/
        top10CategoryCountInfo.map(_.categoryId).foreach(cid => {
            val onlyCidRDD: RDD[(Long, Iterable[(String, Int)])] = itRDD.filter(_._1.toString == cid)
            //onlyCidRDD.collect.foreach(println) //打印测试
            val result: Array[CategorySession] = onlyCidRDD.flatMap { //添加Iterable[(String, Int)]
                case (_, it) => it
            }
                .sortBy(-_._2, ascending = false)
                .take(10)
                .map {
                    case (sid, count) => CategorySession(cid, sid, count)
                }
            // 写入外部存储 jdbc,hbase,hive
            result.foreach(println) //打印测试
            println("-----------")
        })
    }
}
