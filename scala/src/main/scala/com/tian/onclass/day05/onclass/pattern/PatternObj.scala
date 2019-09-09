package com.tian.onclass.day05.onclass.pattern

/**
 * 对象匹配
 *
 * @author tian
 *         2019/9/9 16:19
 */
/*
对象提取器
 */
object PatternObj {
    def main(args: Array[String]): Unit = {
        val r: Double = 9
    }
}

object Sqrt {
    def unapply(arg: Double): Option[Double] = ???
}

//TODO 视频对象提取器
