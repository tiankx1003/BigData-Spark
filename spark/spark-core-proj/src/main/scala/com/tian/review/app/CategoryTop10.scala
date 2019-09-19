package com.tian.review.app

import com.tian.review.acc.CategoryACC
import com.tian.review.bean.{CategoryCountInfo, UserVisitAction}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
 * 需求1: top10的热门品类
 *
 * @author tian
 * @date 2019/9/19 11:08
 * @version 1.0.0
 */
object CategoryTop10 {
    /**
     * 通过累加器统计cid每种action的次数
     * 按照cid分组，并按照action次数降序排序取前十
     *
     * @param sc            上下文对象
     * @param userActionRDD 用户行为数据集
     * @return
     */
    def categoryTop10(sc: SparkContext, userActionRDD: RDD[UserVisitAction]): List[CategoryCountInfo] = {
        val acc: CategoryACC = new CategoryACC
        sc.register(acc) //向executor注册累加器
        userActionRDD.foreach(action => { //通过累加器计算品类id的点击、下单和支付数
            acc.add(action)
        })
        //Map[("cid","action"),count]
        val top10: List[CategoryCountInfo] = acc
            .map.groupBy(_._1._1) //按照品类id分组 (cid,Map[("cid","action"),count])
            .map {
                case (cid, map) => CategoryCountInfo( //封装成对象
                    cid,
                    map.getOrElse((cid, "click"), 0L),
                    map.getOrElse((cid, "order"), 0L),
                    map.getOrElse((cid, "pay"), 0L)
                )
            } //immutable.Iterable[CategoryCountInfo]
            .toList
            .sortBy(info => (-info.clickCount, -info.orderCount, -info.payCount)) //排序
            .take(10) //取前十
        //println(top10) //输出验证
        top10 //返回值用于另一个需求
    }
}
