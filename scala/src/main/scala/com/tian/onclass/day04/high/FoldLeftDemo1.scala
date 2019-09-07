package com.tian.onclass.day04.high

/**
 * 左折叠
 *
 * @author tian
 *         2019/9/7 15:39
 */
object FoldLeftDemo1 {
    def main(args: Array[String]): Unit = {
        val list = List(20, 30, 70, 60)
        //def foldLeft[B](z: B)(@deprecatedName('f) op: (B, A) => B): B
        val str = list.foldLeft("")(_ + _)
        println(str)
    }
}
