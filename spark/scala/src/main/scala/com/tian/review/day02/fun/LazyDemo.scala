package com.tian.review.day02.fun

/**
 * @author tian
 *         2019/9/4 20:03
 */
object LazyDemo {
    lazy val a0 = {
        println("a...")
        10
    }

    def main(args: Array[String]): Unit = {
        println(a0)
        println(a0)
    }

    // 执行时机
    val a = 10 //第一次加载object时执行
    lazy val b = 10 //第一次调用b时执行
    def c = 10 //每次调用时执行

}
