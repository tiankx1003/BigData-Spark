package com.tian.review.day03.exam

/**
 * 设计一个Point类，其x和y坐标可以通过构造器提供。
 * 提供一个子类 LabeledPoint，其构造器接受一个标签值和x,y坐标,
 * 比如:new LabeledPoint(“Black Thursday”,1929,230.07)
 *
 * @author tian
 *         2019/9/6 19:55
 */
object PointDemo2 {
    def main(args: Array[String]): Unit = {
        val p1 = new LabeledPoint("Black Thursday", 1929, 230.07)
        println(p1)
    }
}

class Point2(var px: Double, var py: Double)

class LabeledPoint(var label: String, var x: Double, var y: Double)
    extends Point2(x, y) {
    override def toString: String = s"label=$label,x=$x,y=$y"
}