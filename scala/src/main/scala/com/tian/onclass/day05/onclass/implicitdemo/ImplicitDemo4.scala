package com.tian.onclass.day05.onclass.implicitdemo

/**
 * 隐式类
 *
 * @author tian
 *         2019/9/9 11:51
 */
object ImplicitDemo4 {
    def main(args: Array[String]): Unit = {
        //        implicit def int2RichData1(day:Int) = new RichData1()
        val ago = ""
        1 days ago
    }

    /*
    构造器只接收一个参数
     */
    implicit class RichData1(day: Int) {
        def days(when: String) = ""
    }

}
