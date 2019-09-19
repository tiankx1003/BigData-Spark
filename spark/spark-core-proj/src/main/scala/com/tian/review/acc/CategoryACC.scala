package com.tian.review.acc

import com.tian.review.bean.UserVisitAction
import org.apache.spark.util.AccumulatorV2

/**
 * 自定义累加器
 *
 * @author tian
 * @date 2019/9/19 10:25
 * @version 1.0.0
 */
class CategoryACC extends AccumulatorV2[UserVisitAction, Map[(String, String), Long]] {
    //缓存中间值
    var map = Map[(String, String), Long]()

    //判断零值
    override def isZero: Boolean = map.isEmpty

    //拷贝累加器到executor
    override def copy(): AccumulatorV2[UserVisitAction, Map[(String, String), Long]] = {
        var acc = new CategoryACC
        acc.map ++= map
        acc
    }

    //重置中间值
    override def reset(): Unit = map = Map[(String, String), Long]()

    /**
     * 累加，核心业务逻辑
     * 点击，下单，付款
     *
     * @param v
     */
    override def add(v: UserVisitAction): Unit = { // TODO: 业务逻辑 ?
        //Map[("cid", "action"), count]
        if (v.click_category_id != -1) { //点击
            map += (v.click_category_id.toString, "click") -> (map.getOrElse((v.click_category_id.toString, "click"), 0L) + 1L)
        } else if (v.order_category_ids != "null") { //下单
            val ids = v.order_category_ids.split(",")
            ids.foreach(id => {
                map += (v.order_category_ids.toString, "order") -> (map.getOrElse((v.order_category_ids.toString, "order"), 0L) + 1L)
            })
        } else if (v.pay_category_ids != "null") { //付款
            val ids = v.pay_category_ids.split(",")
            ids.foreach(id => {
                map += (v.pay_category_ids.toString, "pay") -> (map.getOrElse((v.order_category_ids.toString, "order"), 0L) + 1L)
            })
        }
    }

    /**
     * 合并累加器
     *
     * @param other
     */
    override def merge(other: AccumulatorV2[UserVisitAction, Map[(String, String), Long]]): Unit = {
        //品类个数未知
        other match {
            case o: CategoryACC => o.map.foreach {
                case (cidAction, count) =>
                    this.map += cidAction -> (this.map.getOrElse(cidAction, 0L) + count)
            }
            case _ => throw new Exception
        }
    }

    override def value: Map[(String, String), Long] = map
}
