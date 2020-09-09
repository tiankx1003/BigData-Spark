package com.tian.review.day02.highfun

/**
 * 验证scala中是名调用
 *
 * @author tian
 *         2019/9/5 9:44
 */
object ControlAbs1 {
    def main(args: Array[String]): Unit = {
        def f = () => {
            println("a...")
            10
        }
        println(f())
        foo(f())
        foo2(f())
    }

    def foo(a: => Int) = { //名调用
        println(a)
        println(a)
        println(a)
    }

    def foo2(a: Int) = { //值调用
        println(a)
        println(a)
        println(a)
    }
}
