package com.tian.review.day04.high

/**
 * @author tian
 *         2019/9/12 8:52
 */
object ReduceDemo {
    def main(args: Array[String]): Unit = {
        val list1 = List(10, 20, 30, 40)
        val list2 = list1.reduce((x, y) => x + y)
        val list3 = list1.reduce(_ + _)
        println(list1.sum == list2)
        val str1 = List("aa", "bb", "cc")
        val str2 = str1.reduce(_ + "-" + _) //拼接
        //TODO 当前进度
    }
}
