package com.tian.onclass.day05.implicitdemo

/**
 * @author tian
 *         2019/9/9 14:08
 */
/*
隐式转换函数、隐式类、隐式值的查找
当前作用域 -> 相关类型的伴生对象中查找

*伴生对象中的变量都是静态的，可以直接调用
 */
object ImplicitDemo6 {
    def main(args: Array[String]): Unit = {
        foo
    }

    def foo(implicit a: A[B]) = {}
}
object A
class A[B]
class B
object B {
    implicit val a: A[B] = new A[B]
}