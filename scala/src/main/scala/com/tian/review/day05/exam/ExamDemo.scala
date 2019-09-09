package com.tian.review.day05.exam

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * @author tian
 *         2019/9/9 19:44
 */
object ExamDemo {
    def main(args: Array[String]): Unit = {
        println(wcSort(List(("hello", 1), ("world", 3), ("scala", 5), ("ok", 5))))
        println(swap(Array(1, 2, 4, 10)).mkString(", "))
        println(5 !)
        println(120 +% 10)
    }

    /**
     * 1.对前面 wordcount 的结果, 按照单词的个数降序排列,如果个数相同按照单词的长度升序排列
     *
     * @param list
     * @return
     */
    def wcSort(list: List[(String, Int)]) = {
        list.sortBy(a => (a._2, a._1.length))(Ordering.Tuple2(Ordering.Int.reverse, Ordering.Int))
    }

    /**
     * 2.利用模式匹配，编写一个 swap 函数，交换数组中前两个元素的位置，前提条件是数组长度至少为2
     *
     * @param arr
     * @return
     */
    def swap(arr: Array[Int]): Array[Int] = {
        if (arr.length < 2) return Array()
        arr match {
            case Array(a, b, c@_*) => {
                arr(0) = b
                arr(1) = a
            }
        }
        arr
    }

    /**
     * 3.定义一个 ! 操作符, 计算某个整数的阶乘. 比如 5! 应该得到 120
     *
     * @param a
     */
    implicit class MyRichPrime(a: Int) {
        def ! = {
            if (a <= 1) 1
            else {
                var prime = 1
                for (elem <- 2 to a) prime *= elem
                prime
            }
        }
    }

    /**
     * 4.定义一个操作符 +% , 可以将一个给定的百分百添加到某个值
     * 比如 120 +% 10  应该得到 132
     *
     * @param a
     */
    implicit class MyRichPercent(a: Int) {
        def +%(b: Int): Int = a + a * b / 100
    }

}



