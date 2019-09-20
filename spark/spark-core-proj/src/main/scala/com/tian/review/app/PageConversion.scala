package com.tian.review.app

import java.text.DecimalFormat

import com.tian.review.bean.UserVisitAction
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
 * @author tian
 * @date 2019/9/20 20:42
 * @version 1.0.0
 */
object PageConversion {
    def pageConversion(sc: SparkContext, userActionRDD: RDD[UserVisitAction], pages: String) = {
        //得到目标跳转流
        val splits = pages.split(",")
        val prePages = splits.take(splits.length - 1)
        val postPages = splits.tail
        val pageFlow = prePages
            .zip(postPages)
            .map {
                case (pre, post) => pre + "->" + post
            }
        //计算每个目标的点击量
        val targetPageCount = userActionRDD
            .filter(action => prePages.contains(action.page_id.toString))
            .map(action => (action.page_id, 1))
            .countByKey()
        val result = userActionRDD
            .groupBy(_.session_id) //把相同session的分到一起
            .flatMap({
                case (_, actionIt) =>
                    val list = actionIt.toList.sortBy(_.action_time) //按时间排序
                    val preAction = list.take(list.length - 1)
                    val postActions = list.tail
                    preAction
                        .zip(postActions) //掐尾和去头拉链即得跳转
                        .map {
                            case (preAction, postAction) => preAction.page_id + "->" + postAction.page_id
                        }
                        .filter(pageFlow.contains) //过滤出目标跳转流
                        .map((_, 1))
            })
            .countByKey() //行动算子，reduceByKey + groupByKey
            .map {
                case (flow, flowCount) => //计算比例
                    val page = flow.split("->")(0)
                    val rate = flowCount.toDouble / targetPageCount.getOrElse(page.toLong, Long.MaxValue)
                    val formatter = new DecimalFormat(".00%") //调整格式
                    (flow, formatter.format(rate))
            }
        println(result)
    }
}
