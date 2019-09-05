package com.tian.review.day02.highfun

/**
 * 写一个函数
 * 使一段代码能够运行在子线程上
 *
 * @author tian
 *         2019/9/5 10:16
 */
object ControlAbs3 {
    def main(args: Array[String]): Unit = {
        threadFun {
            println(Thread.currentThread().getName)
            for (elem <- 1 to 100 if elem % 2 == 0) println("Hello")
        }

        threadFun {
            println("Hello World!")
            for (elem <- 1 to 100 if elem % 2 == 1) println("World!")
        }
    }

    def threadFun(f: => Unit) = {
        new Thread() {
            override def run(): Unit = f
        }.start()
    }

}
