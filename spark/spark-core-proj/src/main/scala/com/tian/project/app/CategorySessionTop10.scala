package com.tian.project.app

import com.tian.project.bean.{CategoryCountInfo, UserVisitAction}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
 * @author tian
 * @date 2019/9/18 15:12
 * @version 1.0.0
 */
object CategorySessionTop10 {
    def statCategoryTop10Session(sc: SparkContext,
                                 userVisitActionRDD: RDD[UserVisitAction],
                                 top10CategoryCountInfo: List[CategoryCountInfo]): Unit = {
        val result = userVisitActionRDD
            .filter(action => { // 把包含top10cid的用户点击记录过滤出来
                top10CategoryCountInfo.map(_.categoryId).contains(action.click_category_id.toString)
            })
            .map(action => ((action.click_category_id, action.session_id), 1))
            .reduceByKey(_ + _)
            .map {
                case ((cid, sid), count) => (cid, (sid, count))
            }
            .groupByKey()
            .map {
                case (cid, sidCountIt) => (cid, sidCountIt.toList.sortBy(-_._2).take(10))
            }
        result.collect.foreach(println) //打印测试
    }
}
