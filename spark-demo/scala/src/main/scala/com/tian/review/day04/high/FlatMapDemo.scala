package com.tian.review.day04.high

/**
 * @author tian
 *         2019/9/12 8:42
 */
object FlatMapDemo {
    def main(args: Array[String]): Unit = {
        val list1 = List("Hello", "hadoop test", "test tian", "world hello")
        val list2 = list1.map(_.split("\\W+").toList)
        val list3 = list1.flatMap(_.split(" ")) //一个元素生成一个集合
        val list4 = list1.flatMap(x => List())
    }
}
