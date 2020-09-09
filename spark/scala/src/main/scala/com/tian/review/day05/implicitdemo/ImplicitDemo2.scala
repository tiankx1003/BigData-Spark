package com.tian.review.day05.implicitdemo

/**
 * 隐式转换函数
 * 1 days ago
 * 2 days later
 *
 * @author Friday
 * @version 1.0
 *          2019/9/9 22:45
 */
object ImplicitDemo2 {
    def main(args: Array[String]): Unit = {
        val ago = "ago"
        val later = "later"

        /**
         * 隐式转换函数
         *
         * @param a
         * @return
         */
        implicit def getMyRichData(a: Int) = new MyRichData(a)

        3 days ago
        2 days later
    }
}

class MyRichData(i: Int) {
    def days(s: String) = println(s"$i days " + s)
}

