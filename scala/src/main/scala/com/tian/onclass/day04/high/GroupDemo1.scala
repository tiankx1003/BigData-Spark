package com.tian.onclass.day04.high

/**
 * @author tian
 *         2019/9/7 16:53
 */
object GroupDemo1 {
    def main(args: Array[String]): Unit = {
        val list1 = List(30, 50, 7, 6, 1, 20)
        val map1 = list1.groupBy(_ % 3)
        println(map1)
    }
}
