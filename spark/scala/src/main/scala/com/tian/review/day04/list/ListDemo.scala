package com.tian.review.day04.list

/**
 * @author tian
 *         2019/9/11 20:26
 */
object ListDemo {
    def main(args: Array[String]): Unit = {
        val list1 = List(1, 2, 3, 4)
        val list2 = list1 :+ 100 //在后面添加元素
        val list3 = 100 +: list1 //在前面添加元素
        println(100 :: list1 == list3) //::等价 +:
        val list4 = list2 ++ list3
        println(list2 ::: list3 == list4) //::: 等价 ++
        println(List[Nothing]() == Nil) //Nil为空list等价于List[Nothing]()
        val list5 = 1 :: 2 :: 3 :: Nil //Nil用于组成一个新的List
    }
}
