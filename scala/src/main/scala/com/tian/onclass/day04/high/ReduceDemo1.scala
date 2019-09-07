package com.tian.onclass.day04.high

/**
 * 聚合
 *
 * @author tian
 *         2019/9/7 15:23
 */
object ReduceDemo1 {
    def main(args: Array[String]): Unit = {
        val list1 = List(10, 20, 30, 40, 80)
        val list2 = list1.reduce((x, y) => x + y)
        val list3 = list1.reduce(_ + _)
        println(list2)
        println(list1.sum == list2)
        val ss = List("aa", "bb", "cc")
        val s = ss.reduce(_ + "-" + _)
        println(s)
    }
}
