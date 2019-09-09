package com.tian.review.day04.exam

import scala.io.Source
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
        println(makeString(List(1, 2, 3, 4), ":"))
        println(makeString(List(1), "?"))
    }

    /**
     * 1. 使用 reduce 计算集合中的最大值
     *
     * @param list
     * @return
     */
    def reduceMax(list: List[Int]) = list.reduce(math.max(_, _))

    // a.max(b)

    /**
     * 2. 使用 foldLeft 同时计算最大值和最小值
     *
     * @param list
     * @return
     */
    def maxMin(list: List[Int]): List[Int] =
        list.foldLeft(List(list(0), list(0)))((a: List[Int], b: Int) =>
            List(math.min(a(0), b), math.max(a(1), b)))

    //(Int.maxValue,Int.minValue)避免了空值风险问题

    /**
     * 3. 编写一个函数, 接收一个字符串, 返回一个 Map.
     * 比如: indexes("Helloee")
     * 返回: Map(H->{0}, e -> {1, 5, 6}, ...)   数字其实是下标
     *
     * @param str
     * @return
     */
    def strMap(str: String): Map[String, Int] = {
        str.foldLeft(Map[String, Int]())((String, Int) => {

            null
        })
    }


    /**
     * 4. 实现一个函数，作用与mkString相同，使用foldLeft
     *
     * @param list
     * @tparam T
     * @return
     */
    def makeString[T](list: List[T], separator: String): String = list.head + list.tail.foldLeft("")(_ + separator + _)


    /**
     * 5. 实现一个 scala 版的 wordcount
     * 读取文件, 然后实现Source.
     *
     * @param file
     * @return
     */
    def fileWorldCount(file: File) = {
        val file = //path,windows转义
            s"""
               |
               |""".stripMargin
        val lines = Source.fromFile(file).getLines().toList
        val str =
            lines
                .flatMap(_.split("\\W+")) //使用非单词字符切分
                .filter(_.trim.length > 0) //去除空格
                .groupBy(x => x)
                .map(kv => kv._1 -> kv._2.length)
    }
}


