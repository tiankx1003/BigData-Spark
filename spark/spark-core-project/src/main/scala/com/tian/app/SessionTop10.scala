package com.tian.app

import com.tian.bean.{CategoryCountInfo, CategorySession, UserVisitAction}
import org.apache.spark.{Partitioner, SparkContext}
import org.apache.spark.rdd.RDD

import scala.collection.mutable

/**
 * @author tian
 * @date 2019/9/19 15:39
 * @version 1.0.0
 */
object SessionTop10 {
    def sessionTop10(sc: SparkContext,
                     userActionRDD: RDD[UserVisitAction],
                     categoryTop10List: List[CategoryCountInfo]) = {
        userActionRDD
            .filter(action =>
                categoryTop10List
                    .map(_.categoryId)
                    .contains(action.click_category_id.toString)
            )
            .map(action => ((action.click_category_id, action.session_id), 1))
            .reduceByKey(new MyPartitioner(categoryTop10List.map(_.categoryId)), _ + _)
            .map {
                case ((cid, sid), count) => CategorySession(cid.toString, sid, count)
            }
            .mapPartitions(it => {
                var treeSet = new mutable.TreeSet[CategorySession]
                it.foreach(cs => {
                    treeSet += cs
                    if (treeSet.size > 10)
                        treeSet = treeSet.take(10)
                })
                treeSet.toIterator
            })
            .foreach(println)
    }
}

class MyPartitioner(categoryTop10Info: List[String]) extends Partitioner {
    var indexList: Map[String, Int] = categoryTop10Info.zipWithIndex.toMap

    override def numPartitions: Int = indexList.size

    override def getPartition(key: Any): Int = {
        key match {
            case (cid: Long, _) => indexList(cid.toString)
        }
    }
}
