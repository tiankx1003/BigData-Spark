package com.tian.app

import com.tian.bean.{CategoryCountInfo, UserVisitAction}
import com.tian.com.tian.acc.MyAcc
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
 * @author tian
 * @date 2019/9/19 15:39
 * @version 1.0.0
 */
object CategoryTop10 {
    def categoryTop10(sc: SparkContext, userActionRDD: RDD[UserVisitAction]) = {
        val acc = new MyAcc
        sc.register(acc)
        userActionRDD.foreach(action => {
            acc.add(action)
        })
        acc.map
            .groupBy(_._1._1)
            .map {
                case (cid, map) => CategoryCountInfo(
                    cid,
                    map.getOrElse((cid, "click"), 0L),
                    map.getOrElse((cid, "order"), 0L),
                    map.getOrElse((cid, "pay"), 0L)
                )
            }
            .toList
            .sortBy(info => (-info.clickCount, -info.orderCount, -info.payCount))
            .take(10)
    }
}
