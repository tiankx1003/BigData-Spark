package com.tian.review.day03.pck

/**
 * 默认导包
 * @author tian
 *         2019/9/6 19:15
 */
object PckDemo3 {
    def main(args: Array[String]): Unit = {
        println("aaa")
        math.Pi
        import scala.math._ // 导入包中的静态部分内容
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
