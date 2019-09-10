package com.tian.review.day06.generic

/**
 * 泛型类和泛型方法
 *
 * @author tian
 *         2019/9/10 18:34
 */
object GenericDemo1 {
    def main(args: Array[String]): Unit = {
        val point1 = new Point(10, 20)
        val point2 = new Point[Int](10, 20)
        //val point3 = new Point[Int](10,20.0) //编译报错
    }

    /**
     * 泛型方法
     *
     * @param s 传参
     * @tparam T 该方法内可以使用的泛型
     */
    def f0[T](s: String) = println(s)
}

/**
 * 泛型类
 *
 * @param a 属性值
 * @param b 属性值
 * @tparam T 类中可以使用的泛型
 */
class Point[T](val a: T, val b: T) {
    def f1(a: T): T = a
}
