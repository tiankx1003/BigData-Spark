package com.tian.onclass.day04.high

/**
 * @author tian
 *         2019/9/7 15:10
 */
object FilterDemo1 {
    def main(args: Array[String]): Unit = {
        val list1 = List(1, 2, 3, 4, 5, 6)
        val list2 = list1.filter(_ % 2 == 0) //保留偶数
        println(list2)
        val list3 = list1.filter(_ % 2 == 0).map(x => x * x)
        println(list3)
    }
}
