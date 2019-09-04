package com.tian.preview.day02.onclass

import scala.collection.parallel.immutable

/**
 * @author tian
 *         2019/9/4 9:02
 */
object ForDemo1 {
    def main(args: Array[String]): Unit = {
        for (i <- 1 to 9) {
            println(i) // i本质上是常量
        }

        for (i <- 1 to 9) {
            for (j <- 1 to i) {
                print(s"$j * $i = ${j * i}")
                if (i == j) println()
            }
        }
        for (i <- 1 to 9; j <- 1 to i) { // 循环嵌套
            print(s"$j * $i = ${j * i}")
            if (i == j) println()
        }
        // for推导
//        val arr: immutable.IndexedSeq[Int] = for (i <- 1 to 10) yield i * i * i
        // TODO 更优雅的写法
    }
}
