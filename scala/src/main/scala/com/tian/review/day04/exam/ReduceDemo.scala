package com.tian.review.day04.exam

import scala.reflect.io.File


/**
 *
 * @author Friday
 * @version 1.0
 *          2019/9/9 0:35
 */
object ReduceDemo {
    def main(args: Array[String]): Unit = {
        println(reduceMax(List(1, 4, 6, 2)))
        println(maxMin(List(10, 2, 44, 2, 10, 1, 56)))
    }

    /**
     * 1. 使用 reduce 计算集合中的最大值
     *
     * @param list
     * @return
     */
    def reduceMax(list: List[Int]) = list.reduce(math.max(_, _))

    /**
     * 2. 使用 foldLeft 同时计算最大值和最小值
     *
     * @param list
     * @return
     */
    def maxMin(list: List[Int]): List[Int] = {
        list.foldLeft(List(list(0), list(0)))((a: List[Int], b: Int) =>
            List(math.min(a(0), b), math.max(a(1), b))
        )
    }

    /**
     * 3. 编写一个函数, 接收一个字符串, 返回一个 Map.
     * 比如: indexes("Helloee")
     *
     * @param str
     */
    def strMap(str: String) = {

    }

    /**
     * 4. 实现一个函数，作用与mkString相同，使用foldLeft
     *
     * @param arr
     * @tparam T
     * @return
     */
    def makeString[T](arr: Array[T]): String = {

        ""
    }

    /**
     * 5. 实现一个 scala 版的 wordcount
     * 读取文件, 然后实现
     * Source.
     *
     * @param file
     * @return
     */
    def fileWorldCount(file: File): Map[String, Int] = {
        null
    }

    //TODO

}


