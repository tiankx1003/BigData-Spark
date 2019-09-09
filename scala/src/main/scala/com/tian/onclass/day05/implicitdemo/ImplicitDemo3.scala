package com.tian.onclass.day05.implicitdemo

/**
 * 隐式方法
 *
 * @author tian
 *         2019/9/9 11:47
 */
object ImplicitDemo3 {
    def main(args: Array[String]): Unit = {
        implicit def int2RichData(day: Int) = new RichData()

        val ago = ""
        1 days ago
    }
}

class RichData {
    def days(when: String) = ""
}
