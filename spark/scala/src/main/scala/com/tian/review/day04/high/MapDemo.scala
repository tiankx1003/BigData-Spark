package com.tian.review.day04.high

/**
 * @author tian
 *         2019/9/9 19:16
 */
object MapDemo {
    def main(args: Array[String]): Unit = {
        val list1 = List(1, 2, 3, 4, 5)
        val list2 = list1.map(x => x * x)
        val list3 = list1.map(x => (x, x * x))
        val str = "abc".map(_ + 3)
        val list4 = List(30, 40, 50)
        list4.map(x => x)
        list4.map(_) //部分应用函数
    }
}
