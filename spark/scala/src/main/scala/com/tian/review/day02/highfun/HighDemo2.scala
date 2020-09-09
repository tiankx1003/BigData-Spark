package com.tian.review.day02.highfun

/**
 * @author tian
 *         2019/9/4 20:42
 */
object HighDemo2 {
    def main(args: Array[String]): Unit = {
        operation(Array(1, 2, 3), (ele: Int) => { //传入的参数为匿名函数
            print(ele) // 匿名函数的函数体
        })
        // 简写形式
        operation(Array(1, 2, 3), i => print(i)) // 函数体只有一行语句省略大括号
        operation(Array(1, 2, 3), print(_)) // 每个参数只使用一次，省略参数的说明
    }

    def operation(arr: Array[Int], op: Int => Unit) =
        for (i <- arr) op(i)
}
