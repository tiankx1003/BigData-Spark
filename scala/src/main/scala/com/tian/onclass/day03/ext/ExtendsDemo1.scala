package com.tian.onclass.day03.ext

/**
 * @author tian
 *         2019/9/6 10:41
 */
object ExtendsDemo1 {

}

class A {
    val n = 0

    def m = 0

    def f(a: Int) = 0

    var j = 1
}

class B extends A {
    override val n: Int = 100

    override def m: Int = 100

    //    override val f: Int = 100

    //    override var j = 100 //var只能覆写抽象的var
}
