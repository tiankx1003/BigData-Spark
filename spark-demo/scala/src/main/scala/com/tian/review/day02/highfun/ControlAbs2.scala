package com.tian.review.day02.highfun

/**
 * 名调用的实际使用手法
 *
 * @author tian
 *         2019/9/5 10:09
 */
object ControlAbs2 {
    def main(args: Array[String]): Unit = {
        //既然名调用是传递一段代码，那就用代码块作为传参
        foo({
            println("a...")
            10 //为了返回值满足语法要求
        })
        foo{ //省略小括号
            println("a...")
            10
        }
        foo2 {
            println("a...")
        }
    }

    def foo(a: => Int) = {
        println(a)
    }

    //实际使用时，因为传入的是代码块，往往不限制类型，即Unit
    def foo2(a: => Unit) = {
        println(a) // ()
    }
}
