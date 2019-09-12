package com.tian.review.day04.high

/**
 * 主要用于和外界存储系统的交流
 *
 * @author tian
 *         2019/9/12 8:30
 */
object ForeachDemo {
    def main(args: Array[String]): Unit = {
        val list1 = List(10, 20, 30, 40, 50)
        list1.foreach(println(_))
        "aabbccdd".foreach(println(_))
    }
}
