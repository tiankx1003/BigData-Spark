package com.tian.review.day02.onclass

/**
 * @author tian
 *         2019/9/4 10:27
 */
/*
参数默认值,方法在声明时为参数指定默认值，当调用方法不传入相应参数时，
命名参数，直接指定传入参数
 */
object FunDemo2 {
    def main(args: Array[String]): Unit = {
        add(4, 3)
        add(4) // 根据默认值补齐另一个传参
        add2(10, c = 20) // 命名参数，在有多个默认值的函数中使用命名参数
        add2(a = 10, c = 20, b = 30) // 使用命名参数指定值时，可以不考虑位置
    }

    def add(a: Int, b: Int = 3) = { // 形参设定默认值
        a + b
    }

    def add2(a: Int, b: Int = 3, c: Int) = {

    }

    def add3(a: Int) = {
        -a
    }

    def add4(b: Int) = print(b) //函数只有一行时省略大括号

    //    TODO 至简函数
}
