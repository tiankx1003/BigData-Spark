package com.tian.onclass.day05.implicitdemo

/**
 * 隐式转换
 *
 * @author tian
 *         2019/9/9 11:18
 */
/*
隐式转换
隐式转换函数
    implicit def double2Int(d:Double):Int = d.toInt
    同一种隐式转换函数只能有一个

隐式类

隐式参数和隐式值(配合使用)



 */
object ImplicitDemo1 {
    def main(args: Array[String]): Unit = {
        implicit def double2Int(d:Double):Int = d.toInt
        val n: Int = 10.3 // 自动寻找implicit修饰的传参只有Double返回Int的函数
        println(n)
    }
}
