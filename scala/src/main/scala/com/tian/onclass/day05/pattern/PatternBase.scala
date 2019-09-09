package com.tian.onclass.day05.pattern

import scala.io.StdIn

/**
 * @author tian
 *         2019/9/9 14:52
 */
object PatternBase {
    def main(args: Array[String]): Unit = {
        val a = 1
        val b = 2
        val op = StdIn.readLine("input op")
        op match { //case中没有匹配到的内容则抛出异常
            case "+" => println(a + b)
            case "-" => println(a - b)
            case "*" => println(a * b)
            case "/" => println(a / b)
            case _ => println("none") //以上case之外的所有情况，避免没有匹配到时抛出异常
        }
    }
}
