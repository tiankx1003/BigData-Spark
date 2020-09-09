package com.tian.onclass.day04.high

/**
 * @author tian
 *         2019/9/7 16:24
 */
/*
拉链，把两个集合合并到一块

 */
object ZipDemo1 {
    def main(args: Array[String]): Unit = {
        val list1 = List(1,2,3,4)
        val list2 = List(10,20,30,40,50,60)
        val list3 = list1.zip(list2) //以少的为准
        val list4 = list1 zip list2 //简写
        println(list3)
        println(list4)
        val list5 = list1.zipAll(list2,-1,-2) //以多的为准，谁少就用设置的值配对
        println(list5)
        val list6 = list1.zipWithIndex.filter(_._1 % 2 == 0).map(_._1) //保留索引为偶数的
        println(list6)

    }
}
