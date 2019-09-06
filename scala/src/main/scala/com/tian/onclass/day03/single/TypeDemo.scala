package com.tian.onclass.day03.single

/**
 * 类型的判断和转换
 *
 * @author tian
 *         2019/9/6 15:23
 */
object TypeDemo {
    def main(args: Array[String]): Unit = {
        var b: AAA = new BBB
        println(b.isInstanceOf[BBB])
        println(b.isInstanceOf[AAA])
        b.asInstanceOf[BBB].f0
    }
}

class AAA

class BBB extends AAA {
    def f0 = {
        println("f0...")
    }
}
