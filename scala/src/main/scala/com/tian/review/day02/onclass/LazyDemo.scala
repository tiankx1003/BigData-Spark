package com.tian.review.day02.onclass

/**
 * @author tian
 *         2019/9/4 11:14
 */
object LazyDemo {
    /*
    惰性求值适用场景为某些极个别时机使用才加载，提高了运行效率(不需要每次一开始就加载所有)
     */
    lazy val a = { // 惰性求值只在第一次调用时执行
        print("Hello")
        10
    }

    def main(args: Array[String]): Unit = {
        println(a)
    }

    // 下面三种情况的加载时机各不相同
    val x = 10
    lazy val y =10
    def z = 10
}
