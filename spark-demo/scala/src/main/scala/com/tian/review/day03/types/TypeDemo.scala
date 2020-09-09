package com.tian.review.day03.types

/**
 * 类型判断和转换
 *
 * @author tian
 *         2019/9/7 8:31
 */
object TypeDemo {
    def main(args: Array[String]): Unit = {
        val b: A6 = new B6
        println(b.isInstanceOf[B6])
        println(b.isInstanceOf[A6])
        //        b.f0
        b.asInstanceOf[B6].f0 //类型转换后调用函数
    }
}

class A6

class B6 extends A6 {
    def f0 = println("bbbb")
}
