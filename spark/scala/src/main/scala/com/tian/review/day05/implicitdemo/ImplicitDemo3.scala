package com.tian.review.day05.implicitdemo

/**
 * 隐式类
 * 隐式转换的语法糖
 *
 * @author Friday
 * @version 1.0
 *          2019/9/9 22:53
 */
object ImplicitDemo3 {
    def main(args: Array[String]): Unit = {
        val ago = "ago"
        val later = "later"
        2 days ago
        3 days later
    }

    /**
     * 隐式类，不能写在最外层
     *
     * @param a
     */
    implicit class MyRichDate(a: Int) {
        def days(s: String) = println(s"$a + days " + s)
    }
}
