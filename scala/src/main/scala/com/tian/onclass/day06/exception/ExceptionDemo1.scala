package com.tian.onclass.day06.exception

import java.lang

import scala.util.control

/**
 * @author tian
 *         2019/9/10 10:15
 */
/*
java
    编译时(受检异常)
        代码中必须处理
            try

            抛
                抛声明
                    throws 异常类，异常类
                抛对象
                    throws 异常对象
    运行时
        预计有异常，可以提前处理

scala

 */
object ExceptionDemo1 {
    def main(args: Array[String]): Unit = {
        Thread.sleep(1000) //java中必须try-catch，scala中不需要
        try {
            //            val a = 1 / 0
            throw new IllegalArgumentException("参数非法")
        } catch {
            case e: ArithmeticException => println(e)
            case e: RuntimeException => println(e)
            case e: Exception => println(e)
            case _ =>
        } finally {
            println("finally")
        }


//        f0("100")
        try {
            f0("aaa")
        } catch {
            case e:IllegalArgumentException => println(e)
        }
    }

    @throws(classOf[IllegalArgumentException])
    def f0(a: String) = {
        a.toInt
    }
}
