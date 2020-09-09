package com.tian.review.day05.implicitdemo

/**
 * 隐式值和隐式函数的查找
 * 优先查找当前作用域，若没有则查找相关类型的伴生对象
 *
 * @author Friday
 * @version 1.0
 *          2019/9/9 23:19
 */
object ImplicitDemo6 {
    def main(args: Array[String]): Unit = {
        fun
    }
    def fun(implicit a:A[B]) = println("implicit")
}

class A[B]
class B
object B{ //相关类型的伴生对象
    implicit val a:A[B] = new A[B]
}
