package com.tian.onclass.day04.high

/**
 * 折叠简写
 *
 * @author tian
 *         2019/9/7 16:20
 */
object FoldLeftDemo3 {
    def main(args: Array[String]): Unit = {
        val list1 = List(30, 50, 70, 90)
        val result = (0 /: list1) (_ + _) //左折叠
        val result2 = (list1 :\ 0) (_ + _) //右折叠
        println(result)
        println(result2)
    }
}
