package com.tian.review.day02.onclass

/**
 * @author tian
 *         2019/9/4 16:16
 */
/*
写一个函数，可以运行一段代码在子线程中
 */
object ControlAbs2 {
    def main(args: Array[String]): Unit = {
        runInThread{
            //
            println(Thread.currentThread().getName)
        }

    }
    def runInThread(f: => Unit) ={
        new Thread(){
            override def run(): Unit = f
        }.start()
    }

}
