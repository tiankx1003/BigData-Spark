package com.tian.preview.day02.onclass

/**
 * @author tian
 *         2019/9/4 15:38
 */
/*
递归
1.终止条件
2.靠近终止条件

 */
//TODO 补足
object RecusiveDemo {
    def main(args: Array[String]): Unit = {
        println(jiecheng(10))
    }

    def jiecheng(n: Long): Long = {
        if (n == 1) 1
        else n * jiecheng(n - 1)
    }
}
