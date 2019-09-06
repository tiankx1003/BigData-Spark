package com.tian.review.day03.modifier

/**
 * 权限修饰符
 *
 * @author tian
 *         2019/9/6 21:09
 */
object ModifierDemo
class A5{
    //指定包以及它的子包都可以访问
    private[modifier] def foo() = {}
    foo()
}
class B5 extends A5{
    val a = new A5
    a.foo() //定制后才能访问
}
