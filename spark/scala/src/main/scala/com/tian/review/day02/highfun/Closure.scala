package com.tian.review.day02.highfun

/**
 * 闭包是函数式编程的标配
 * 如果一个函数调用了外部(局部)的变量，那么这个函数和它所处的环境称为闭包
 * 由于java没有闭包，所以内部类无法访问外部的局部变量
 *
 * 函数柯里化
 * 把多个参数转化为多个形参列表
 *
 * @author tian
 *         2019/9/5 9:33
 */
object Closure {
    def main(args: Array[String]): Unit = {
        //写法演进
        //        val add4: Int => Int = add(4)
        val add4 = add(4)
        val add45 = add4(5)
        val num45 = add(4)(5)
    }
    def add(a: Int) = (b: Int) => a + b
    def add1(a: Int, b: Int) = a + b
    //柯里化
    def add2(a: Int)(b: Int) = a + b
}
