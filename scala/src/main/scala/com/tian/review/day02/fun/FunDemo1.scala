package com.tian.review.day02.fun

/**
 * @author tian
 *         2019/9/4 18:53
 */
object FunDemo1 {
    def main(args: Array[String]): Unit = {

    }

    //函数的定义,常规写法，简写见至简原则
    def add0(a: Int, b: Int): Int = {
        return a + b
    }

    //可变参量
    def add1(arr: Int*) = {
        var sum = 0
        for (elem <- arr) {
            sum += elem
        }
        sum
    }

    //可变参量出于形参列表的最后
    def add2(a: Double, b: Int, arr: Int*) = {
        var sum = 0
        for (elem <- arr) {
            sum += elem
        }
        sum * a + b
    }
}
