package com.tian.review.day03.exam

/**
 * 定义一个 Point 类和一个伴生对象,
 * 使得我们可以不用 new 而直接用 Point(3,4)来构造 Point 实例
 *
 * @author tian
 *         2019/9/6 19:30
 */
object Points {
    def main(args: Array[String]): Unit = {
        Point(3, 4)
    }
}

object Point {
    def apply(x: Double, y: Double): Point = new Point(x, y)
}

class Point(x: Double, y: Double) {

}
