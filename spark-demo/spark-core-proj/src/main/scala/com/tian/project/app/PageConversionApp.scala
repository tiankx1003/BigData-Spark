package com.tian.project.app

import java.text.DecimalFormat

import com.tian.project.bean.UserVisitAction
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

import scala.collection.immutable.TreeMap

/**
 * @author tian
 * @date 2019/9/20 9:20
 * @version 1.0.0
 */
object PageConversionApp {
    def calcPageConversion(sc: SparkContext, userVisitActionRDD: RDD[UserVisitAction], pages: String): Unit = {
        // 1.得到目标跳转流
        val splits: Array[String] = pages.split(",")
        val prePages: Array[String] = //splits.slice(0, splits.length - 1)
            splits.take(splits.length - 1)
        val postPages: Array[String] = //splits.slice(1, splits.length)
            splits.tail
        val pageFlow: Array[String] = prePages.zip(postPages).map {
            case (pre, post) => pre + "->" + post
        }
        // 2.计算每个目标的点击量
        val targetPageCount: collection.Map[Long, Long] = userVisitActionRDD
            .filter(action => prePages.contains(action.page_id.toString))
            .map(action => (action.page_id, 1))
            //.reduceByKey(_ + _)
            //.collect() //页面个数有限可直接拉到driver
            .countByKey()
        // 3.计算每个跳转流的数量
        val totalPageFlowsRDD: collection.Map[String, Long] = userVisitActionRDD
            //.filter(action => splits.contains(action.page_id.toString)) //先过滤能减少后续操作的数据，有问题
            .groupBy(_.session_id) //把相同session的分到一起
            .flatMap {
                case (_, actionIt) =>
                    val list: List[UserVisitAction] = actionIt.toList.sortBy(_.action_time) //应该按照时间排序
                    val preActions: List[UserVisitAction] = list.take(list.length - 1)
                    val postActions: List[UserVisitAction] = list.tail
                    val totalPageFlows: List[String] = preActions.zip(postActions).map {
                        case (preAction, postAction) => preAction.page_id + "->" + postAction.page_id
                    }
                    totalPageFlows
                        .filter(pageFlow.contains)
                        //totalPageFlows.filter(flow => pageFlow.contains(flow))
                        .map((_, 1))
            }
            .countByKey()
        // 4.计算跳转率
        val result = totalPageFlowsRDD.map {
            case (flow, flowCount) =>
                var treeMap = TreeMap[String, Double]()
                val page: String = flow.split("->")(0)
                val rate: Double = flowCount.toDouble / targetPageCount.getOrElse(page.toLong, Long.MaxValue)
                val formatter: DecimalFormat = new DecimalFormat(".00%")
                (flow, formatter.format(rate))
        }
        println("")
        println(result)
        // TODO: 尝试使用广播变量
    }
}
