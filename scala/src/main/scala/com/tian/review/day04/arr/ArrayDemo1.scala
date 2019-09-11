package com.tian.review.day04.arr

/**
 * Array
 *
 * @author tian
 *         2019/9/11 19:55
 */
object ArrayDemo1 {
    def main(args: Array[String]): Unit = {//main函数的参数args为定长数组val args
        val arr0 = new Array[Int](10) //默认为十个0
        val arr1 = Array(1, 2, 3)
        arr0(0) = 100
        println(arr0(0))
        println(arr0.length)
        println(arr0.size)
        var arr2 = Array(20, 30, 40)
        println(arr2)
        arr2 :+= 20 //对于定长数组而言是旧数组和新元素拼接成一个新的数组
        println(arr2)
    }
}
