package com.tian.review.day05.pattern

import scala.io.StdIn

/**
 * @author tian
 *         2019/9/9 19:27
 */
object PatternBase {
    def main(args: Array[String]): Unit = {
        val a = 10
        val b = 15
        val input = StdIn.readLine("input option: ")
        input match {
            case "+" => println(a + b)
            case "-" => println(a - b)
            case "*" => println(a * b)
            case "/" => println(a / b)
            case _ => println("input err") //其他类型
        }
    }
}
