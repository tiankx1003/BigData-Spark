package com.tian.review.day02.highfun.loop

/**
 * @author tian
 *         2019/9/4 18:39
 */
object ForDemo1 {
    def main(args: Array[String]): Unit = {
        //输出九九乘法表
        for (i <- 1 to 9) {
            for (j <- 1 to i) {
                print(s"$j * $i = ${j * i}\t")
            }
            println()
        }

        //使用for的嵌套
        for (i <- 1 to 9; j <- 1 to i) {
            print(s"$j * $i = ${j * i}\t")
            if (j == i) println()
        }
    }

}
