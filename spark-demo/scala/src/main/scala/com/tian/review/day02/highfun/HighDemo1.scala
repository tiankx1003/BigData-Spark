package com.tian.review.day02.highfun

/**
 * @author tian
 *         2019/9/4 20:25
 */
object HighDemo1 {
    def main(args: Array[String]): Unit = {
        def fun1 = print("f1...")

        val f = fun1 _ //函数的传递
        f() //调用函数
        println(highFun(add))
    }

    //高阶函数
    def highFun(f: (Int, Int) => Int) = {
        f(2, 4)
    }

    def add(a: Int, b: Int) = a + b

}
