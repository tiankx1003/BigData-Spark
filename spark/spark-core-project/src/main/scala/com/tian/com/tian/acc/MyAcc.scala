package com.tian.com.tian.acc

import com.tian.bean.UserVisitAction
import org.apache.spark.util.AccumulatorV2

/**
 * 累加器
 *
 * @author tian
 * @date 2019/9/19 15:40
 * @version 1.0.0
 */
class MyAcc extends AccumulatorV2[UserVisitAction, Map[(String, String), Long]] {
    var map: Map[(String, String), Long] = Map[(String, String), Long]()

    override def isZero: Boolean = map.isEmpty

    override def copy(): AccumulatorV2[UserVisitAction, Map[(String, String), Long]] = {
        val acc = new MyAcc
        acc.map ++= map
        acc
    }

    override def reset(): Unit = map = Map[(String, String), Long]()

    override def add(v: UserVisitAction): Unit = {
        if (v.click_category_id != -1) {
            map += (v.click_category_id.toString, "click") -> (map.getOrElse((v.click_category_id.toString, "click"), 0L) + 1L)
        } else if (v.order_category_ids != "null") {
            val ids = v.order_category_ids.split(",")
            ids.foreach(cid => {
                map += (cid, "order") -> (map.getOrElse((cid, "order"), 0L) + 1L)
            })
        } else if (v.pay_category_ids != "null") {
            val cid = v.pay_category_ids.split(",")
            cid.foreach(cid => {
                map += (cid, "pay") -> (map.getOrElse((cid, "pay"), 0L) + 1L)
            })
        }
    }

    override def merge(other: AccumulatorV2[UserVisitAction, Map[(String, String), Long]]): Unit = {
        other match {
            case o: MyAcc => {
                o.map.foreach {
                    case (cidAction, count) =>
                        this.map += cidAction -> (this.map.getOrElse(cidAction, 0L) + count)
                }
            }
            case _ => throw new Exception
        }
    }

    override def value: Map[(String, String), Long] = map
}
