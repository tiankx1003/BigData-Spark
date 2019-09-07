package com.tian.onclass.day04.arr

/**
 * @author tian
 *         2019/9/7 9:19
 */
/*
数组
    定长数组
        底层就是java的数组
        1. new Arra有[类型](长度)
        2. 通过初始化元素的个数来直接确定数组的长度

    可变数组

 */
object ArrayDemo1 {
    def main(args: Array[String]): Unit = {
//        val arr = new Array[Int](10) //默认为十个0
        val arr = Array(1,2,3)
        arr(0) = 100
        println(arr(0))
        println(arr(arr.length - 1))
        println(arr.size)
        var arr2 = Array(20,30,40)
        println(arr2)
        arr2 :+= 20 //对于不可变数组而言是添加后成为一个新的数组并赋值
        println(arr2)

    }
}
