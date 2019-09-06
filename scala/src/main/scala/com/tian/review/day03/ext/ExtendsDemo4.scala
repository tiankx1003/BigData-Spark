package com.tian.review.day03.ext

/**
 * 继承案例
 *
 * @author tian
 *         2019/9/6 21:03
 */
object ExtendsDemo4 {

}

class Point(val x: Double, val y: Double) {
    def distance(other: Point) =
        math.sqrt((this.x - other.x) * (this.x - other.x)
            + (this.y - other.y) * (this.y - other.y))
}

class Circle(override val x: Double, override val y: Double, val r: Double)
    extends Point(x, y) {
    def area = math.Pi * r * r
}


