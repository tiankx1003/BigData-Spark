package com.tian.onclass.day04.high

/**
 * @author tian
 *         2019/9/7 14:47
 */
object FlatMapDemo2 {
    def main(args: Array[String]): Unit = {
        val list1 = List(30, 50, 70, 90, 10, 20)
        val list2 = list1.flatMap(x => Array(x, x * x, x * x, x * x))
        println(list2)
        val s = "abc"
        s.flatMap(s => Array(s,s.toUpper))
    }
}
