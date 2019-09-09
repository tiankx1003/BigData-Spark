package com.tian.onclass.day05.onclass.pattern

/**
 * @author tian
 *         2019/9/9 16:12
 */
object PatternUse {
    def main(args: Array[String]): Unit = {
        val arr = Array(10, 20, 30)
        val Array(_, a, b) = arr //底层用了match
        println(a)
        println(b)
        val tuple = BigInt(10) /% 3
        println(tuple._2)
        val (_, t) = BigInt(10) /% 3
        println(t)
        val map = Map(1 -> 2, 2 -> 3)
        for ((k, v) <- map) println(v) //也是用到了模式匹配
    }
}
