package com.tian.review.day03.ext

/**
 * 属性的覆写
 *
 * @author tian
 *         2019/9/6 20:46
 */
object ExtendsDemo2 {
    def main(args: Array[String]): Unit = {
        val b = new B1
        println(b.a)
        println(b.b)
        println(b.n)
    }
}

abstract class A1 {
    def n = 10
    var a: Int
    val b = 20
}

class B1 extends A1 {
    //    override def n: Int = 100
    override val n = 100
    override var a = 100
    override val b = 200
}