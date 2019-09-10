package com.tian.review.day06.pattern

/**
 * 偏函数
 *
 * @author tian
 *         2019/9/10 19:51
 */
object PartialFunDemo1 {
    def main(args: Array[String]): Unit = {
        val f1 = fun1(_ + _) //匿名函数写法
        val f2 = fun1 { //偏函数写法
            case (a, b) => a + b
        }
        println(f1 == f2)
        val f3 = fun2((t, a) => t._1._2 + a) //匿名函数写法
        val f4 = fun2 { //偏函数写法
            case (((_, a), _), c) => a + c
        }
        println(f3 == f4)
    }
    def fun1(f: (Int, Int) => Int) = f(3, 4)
    def fun2(f: (((Int, Int), Int), Int) => Int) = f(((1, 2), 3), 4)
}


