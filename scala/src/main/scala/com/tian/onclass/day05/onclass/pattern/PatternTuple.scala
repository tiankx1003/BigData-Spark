package com.tian.onclass.day05.onclass.pattern

/**
 * 元组匹配
 *
 * @author tian
 *         2019/9/9 16:10
 */
object PatternTuple {
    def main(args: Array[String]): Unit = {
        val t = (1, 2, 3)
        t match {
            case (a, b, c) => println(a)
            case (a, _, c) => println(c)
        }
    }
}

/*
Map和Set等没有匹配，因为无序
 */
