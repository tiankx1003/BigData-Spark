package com.tian.onclass.day04.high

/**
 * GroupBy WordCount
 * @author tian
 *         2019/9/7 16:59
 */
object GroupDemo2 {
    def main(args: Array[String]): Unit = {
        val list1 = List("Hello World", "world", "World", "scala scala")
        val map = list1.flatMap(_.split(" ")).groupBy(word => word).map(kv => (kv._1, kv._2.length))
        println(map)
    }
}
