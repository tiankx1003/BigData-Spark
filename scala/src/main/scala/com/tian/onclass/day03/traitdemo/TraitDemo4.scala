package com.tian.onclass.day03.`trait`

/**
 * @author tian
 *         2019/9/6 16:05
 */
/*
trait继承类

 */
object TraitDemo4 {
    def main(args: Array[String]): Unit = {
//        val console = new Console
//        console.log
    }
}

trait Logger extends Exception {
    def log = {
        println(getMessage)
    }
}

trait Logger2 { // 效果同上
    //    _: Exception =>
    this: Exception => //强制要求实现类混入Exception
    def log = {
        println(getMessage)
    }
}

class Console1 extends Logger //Console继承的直接父类是Exception
class Console2 extends Exception with Logger2 // 等效



