package com.tian.onclass.day04.list

/**
 * @author tian
 *         2019/9/7 10:31
 */
/*
List分为可变和不可变
List 不可变

ListBuffer 可变
 */
object ListDemo1 {
    def main(args: Array[String]): Unit = {
        val list1 = List(1,2,3,4)
        val list2 = list1 :+ 100
        val list3 = 100 +: list1
        println(list2)
        println(list3)
        /*
        :: 和 :::为不可变list专用
         */
        val list4 = 100::list1 // 等价于 +:
        println(list4)
        val list5 = list1 ++ list2
        val list6 = list1 ::: list2 //等价于++ ，list2.:::(list1)
        println(list5)
        println(Nil) //空的list，等价于list[Nothing]()
        val list7 = 1 :: 2 :: 3 :: Nil //Nil用于组成一个新的list
    }
}
