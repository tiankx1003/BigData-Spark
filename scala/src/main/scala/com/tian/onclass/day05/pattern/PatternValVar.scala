package com.tian.onclass.day05.pattern

import scala.io.StdIn

/**
 * 匹配变量
 *
 * @author tian
 *         2019/9/9 14:58
 */
object PatternValVar {
    def main(args: Array[String]): Unit = {
        val op = StdIn.readLine("input op")
        val plus = "+"
        val Plus = "+"
        op match {
            case op => println(op) //匹配所有的值
            case _ => //值再也不用，省下一个变量名
            case plus => println("+") //和match外部的plus不是同一个plus
            case Plus => println("Plus") //常量匹配，常量名的首字母必须大写
            case "a" => println("a")

        }
    }
}
