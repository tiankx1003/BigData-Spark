package com.tian.onclass.day03.ext

/**
 * @author tian
 *         2019/9/6 11:20
 */
object ExtendsDemo3 {
    def main(args: Array[String]): Unit = {
        val circle1 = new Circle(0, 0, 4)
        val circle2 = new Circle(0, 4, 5)
        println(circle1)
        println(circle1.distance(circle2))
        println(circle1.area)
        println(circle2.area)

    }
}

/**
 * 点
 *
 * @param x 横坐标
 * @param y 纵坐标
 */
class Point(val x: Double, val y: Double) {
    def distance(other: Point) = {
        math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y))
    }
}

/**
 * 圆
 *
 * @param x 圆点横坐标
 * @param y 圆点纵坐标
 * @param r 半径
 */
class Circle(override val x: Double, override val y: Double, val r: Double)
    extends Point(x, y) {
    override def toString: String = s"x:$x, y:$y, r:$r"

    def area = math.Pi * r * r
}
