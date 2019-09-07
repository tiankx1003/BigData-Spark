package com.tian.onclass.day04.high

/**
 * @author tian
 *         2019/9/7 14:38
 */
/*
map不会增加长度
flatMap会改变长度(增加，或减小)
传进去的函数的返回值必须是一个集合
 */
object FlatMapDemo {
    def main(args: Array[String]): Unit = {
        val list1 = List("Hello", "hadoop test", "test tian", "world hello")
        val list2 = list1.map(_.split(" ").toList)
        println(list2) //map操作不会改变集合的长度
        val list3 = list1.flatMap(_.split(" ")) //一个元素对应要给集合
        println(list3)
        val list4 = list1.flatMap(x => List())
        println(list4)
    }
}
