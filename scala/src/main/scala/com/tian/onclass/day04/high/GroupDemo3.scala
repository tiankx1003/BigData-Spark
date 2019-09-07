package com.tian.onclass.day04.high

/**
 * WordCount
 * @author tian
 *         2019/9/7 17:02
 */
object GroupDemo3 {
    def main(args: Array[String]): Unit = {
        val list1 = List("hello" -> 1, "hello" -> 5, "world" -> 10, "world" -> 2)
        val groupedMap = list1.groupBy(_._1)
        // TODO
        val tmp1 = groupedMap.map(kv => {
            val word = kv._1
            word -> kv._2.map(_._2).sum
        })
        println(tmp1)
    }
}
