package com.tian.project.acc

import com.tian.project.bean.UserVisitAction
import org.apache.spark.util.AccumulatorV2

/**
 * @author tian
 * @date 2019/9/18 13:54
 * @version 1.0.0
 */
class CategoryAcc extends AccumulatorV2[UserVisitAction, Map[(String, String), Long]] {
    var map: Map[(String, String), Long] = Map[(String, String), Long]()

    override def isZero: Boolean = map.isEmpty

    override def copy(): AccumulatorV2[UserVisitAction, Map[(String, String), Long]] = {
        var acc = new CategoryAcc
        acc.map ++= map
        acc
    }

    override def reset(): Unit = map = Map[(String, String), Long]()


    override def add(v: UserVisitAction): Unit = {
        //键值对("cid","click") -> 点击数
        if (v.click_category_id != -1)
            map += (v.click_category_id.toString, "click") -> (map.getOrElse((v.click_category_id.toString, "click"), 0L) + 1L)
        else if (v.order_category_ids != "null") {
            val orderIds = v.order_category_ids.split(",")
            orderIds.foreach(id => {
                map += (id, "order") -> (map.getOrElse((id, "order"), 0L) + 1L)
            })
        } else if (v.pay_category_ids != "null") {
            val payIds = v.pay_category_ids.split(",")
            payIds.foreach(id => {
                map += (id, "pay") -> (map.getOrElse((id, "pay"), 0L) + 1L)
            })
        }
    }

    override def merge(other: AccumulatorV2[UserVisitAction, Map[(String, String), Long]]): Unit = {
        val o = other.asInstanceOf[CategoryAcc]
        //品类个数未知，不能使用穷举
        o.map.foreach {
            case (cidAction, count) =>
                this.map += cidAction -> (this.map.getOrElse(cidAction, 0L) + count)
        }
    }

    override def value: Map[(String, String), Long] = map
}
