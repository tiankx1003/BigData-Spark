package com.tian.onclass.day03.sub

/**
 * @author tian
 *         2019/9/6 16:35
 */
/*
java中java.lang 自动导入
scala中自动导入java.lang._、 scala._和scala.Predef._ 自动导入
 */
object PckDemo {
    def main(args: Array[String]): Unit = {
        println("aaa")
        import scala.math._ //导入对象中的静态内容
        abs(11 + Pi)
        val user = new User
        user.foo()
        user.foo1()
        import user._
        foo()
        foo1()
    }
}

class User{
    def foo() = {}
    def foo1() = {}
}
