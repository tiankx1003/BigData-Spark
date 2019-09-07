package com.tian.onclass.day04.high

/**
 * @author tian
 *         2019/9/7 15:19
 */
object FilterDemo2 {
    def main(args: Array[String]): Unit = {
        val list1 = List(null, 20, 30, 80, true, "a")
        val list2 = list1.filter(_.isInstanceOf[Int])
            .map(_.asInstanceOf[Int] + 1)
            .map(_ + 1)
        println(list2)
    }
}
