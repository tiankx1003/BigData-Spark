package com.tian.review.day02.onclass

/**
 * @author tian
 *         2019/9/4 15:06
 */
/*
闭包:
函数式编程的标配
如果一个函数，访问到了他的外部(局部)变量的值，
那么这个函数和他所处的环境称为闭包
 */
object ClosureDemo1 {
    def main(args: Array[String]): Unit = {
        var a = 10

        def cls(): Unit = {
            print(a) //访问了外部(局部)变量，闭包
        }

        val add4: Int => Int = add1(4)
        println(add4(4))
        println(add4(5))
        println(add1(4)(5))
        println(add1(5)(5))
    }

    def add(a: Int) = {
        (b: Int) => a + b
    }

    def add1(a: Int) = (b: Int) => a + b

    //简化写法
    def add2(a: Int)(b: Int) = a + b // 函数的柯里化
    // 把一个参数列表的多个参数变为多个参数列表
}
