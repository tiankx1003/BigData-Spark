package com.tian.review.day05.implicitdemo

/**
 * 隐式值 隐式参数
 *
 * @author Friday
 * @version 1.0
 *          2019/9/9 23:09
 */
object ImplicitDemo5 {
    def main(args: Array[String]): Unit = {
        implicit val num = 10 //隐式值
        fun1
        fun2(10)
        fun2(10)(20) //有传参时使用传入的参数
        fun3(10) //使用隐式值
        fun3(10)() //使用默认值
        fun3(10)(20) //使用实参
    }

    def fun1(implicit a: Int) = println(a) //含有隐式参数的函数
    def fun2(a: Int)(implicit b: Int) = println(a + b) //隐式参数柯里化
    def fun3(a: Int)(implicit b: Int = 20) = println(a + b) //隐式参数、柯里化、默认值
}
