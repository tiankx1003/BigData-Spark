package com.tian.review.day02.onclass

/**
 * @author tian
 *         2019/9/4 11:22
 */
/*
1.在scala中函数是一等公民
定义函数 调用函数 传递函数
一个函数可以接收一个函数作为参数，这样的函数就是高阶函数(高阶算子)
 */
object HighDemo1 {
    def main(args: Array[String]): Unit = {
        val f = foo _ // 没有调用函数，只是把函数传递(赋值)给f
        f() // 传递(赋值)后可以通过被赋值后的变量来调用函数
        //        val add = 10
        println(f1(add))
    }

    def foo(): Unit = {
        println("foo...")
        1
    }

    def f1(n: (Int, Int) => Int) = { // 形参可以是一个函数，只限制传入形参的形参类型和返回值类型
        n(2, 4)
    }

    def add(a: Int, b: Int) = a + b

}
