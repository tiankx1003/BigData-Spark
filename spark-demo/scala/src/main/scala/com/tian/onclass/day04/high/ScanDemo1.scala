package com.tian.onclass.day04.high

/**
 * @author tian
 *         2019/9/7 16:15
 */
/*
scan 扫描
fold的扩展版本，记录了fold的过程
 */
object ScanDemo1 {
    def main(args: Array[String]): Unit = {
        val list1 = List(10, 20, 30, 40)
        val list2 = list1.scanLeft(100)(_ + _)
        println(list2)
        val list3 = list1.scanRight(100)(_ + _)
        println(list3)
    }
}
