package com.tian.review.day02.highfun

/**
 * 递归就是自己调用自己
 * 1.设置终止条件
 * 2.逐渐接近终止条件
 *
 * 尾递归
 *
 * 递归调用函数过多，会导致栈溢出
 *
 * @author tian
 *         2019/9/5 9:20
 */
object RecursiveDemo {
    def main(args: Array[String]): Unit = {
        println(factorial(5))
    }

    def factorial(a: Int): Int = {
        if (a == 1) a
        else a * factorial(a - 1)
    }
}
