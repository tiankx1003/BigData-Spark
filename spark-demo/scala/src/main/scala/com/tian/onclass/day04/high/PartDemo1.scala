package com.tian.onclass.day04.high

/**
 * 部分应用函数
 *
 * @author tian
 *         2019/9/7 16:45
 */
object PartDemo1 {
    // TODO 类型确定问题
    def main(args: Array[String]): Unit = {
        println(math.pow(9, 2))/*
        val f = math.pow(_, 2) //返回一个只计算二次方的函数
        println(f(3))
        val add2 = add(_, 2)
        println(add2(3))*/
    }

    def add(x: Int, y: Int) = {
        x + y
    }
}
