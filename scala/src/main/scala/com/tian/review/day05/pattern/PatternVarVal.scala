package com.tian.review.day05.pattern

import scala.io.StdIn

/**
 * 匹配变量
 *
 * @author Friday
 * @version 1.0
 *          2019/9/9 23:34
 */
object PatternVarVal {
    def main(args: Array[String]): Unit = {
        val op = StdIn.readLine("input pls: ")
        val plus = "+"
        val Plus = "+" //常量首字母大写
        op match {
            case op => println(op) //使用变量接收，则任一case都会匹配得到，该值可以被调用
            case _ => //_也可以匹配任意case，但是值不能再后续反复的使用
            case plus => println("+") //这里的plus变量是做模式匹配临时新建的和前面的plus无关
            case Plus => println("+") //首字母大写后match使用的是前面定义好的Plus常量
        }
    }
}
