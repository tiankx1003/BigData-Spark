package com.tian.onclass.day06.pattern

/**
 * 偏函数
 *
 * @author tian
 *         2019/9/10 9:04
 */
/*
如何定义一个偏函数
    用一对大括号括起来的一系列case语句，就是偏函数
TODO 文档内容,只有collect才会判断参数，其他都是直接调用apply
 */
object PartitionFunDemo1 {
    def main(args: Array[String]): Unit = {
        val r1 = f1((a, b) => {
            a + b
        })
        r1
        println(r1)
        val r2 = f1({ //偏函数
            case (a, b) => a + b
        })
        println(r2)

        val m1 = f2((t, c) => {
            t._2 + c
        })
        println(m1)
        val m2 = f2({ //偏函数
            case ((a, b), c) => b + c
        })
        println(m2)
    }

    def f1(f: (Int, Int) => Int) = {
        f(3, 4)
    }

    def f2(f: ((Int, Int), Int) => Int): Unit = {
        f((1, 2), 10)
    }

//    def f3((((Int,Int),Int),Int) => Int) = println("a")
}
