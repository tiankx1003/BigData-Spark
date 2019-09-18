package com.tian.project.app

import com.tian.project.acc.CategoryAcc
import com.tian.project.bean.{CategoryCountInfo, UserVisitAction}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
 * 需求1: top10的热门品类
 *
 * @author tian
 * @date 2019/9/18 11:45
 * @version 1.0.0
 */
/*
1.使用累加器计算出3个指标:品类id点击，下单和支付数
2.按照品类分组，再排序，取top10
 */
object CategoryTop10App {
    def statCategoryTop10(sc: SparkContext, userVisitActionRDD: RDD[UserVisitAction]) = {
        val acc = new CategoryAcc
        sc.register(acc)
        userVisitActionRDD.foreach(action => {
            acc.add(action)
        })
        //acc.value.foreach(x => println(x)) //打印测试
        val actionGroupedByCidMap = acc.map.groupBy(_._1._1) //按照品类id分组
        val categoryCountInfoList = actionGroupedByCidMap.map { //同一品类id的所有行为封装到样例类
            case (cid, map) => CategoryCountInfo(
                cid,
                map.getOrElse((cid, "click"), 0L),
                map.getOrElse((cid, "order"), 0L),
                map.getOrElse((cid, "pay"), 0L)
            )
        }.toList
        val top10 = categoryCountInfoList
            .sortBy(info =>(-info.clickCount,-info.orderCount,-info.payCount)) //降序
            .take(10) //前十
        println(top10) //打印测试
    }
}
