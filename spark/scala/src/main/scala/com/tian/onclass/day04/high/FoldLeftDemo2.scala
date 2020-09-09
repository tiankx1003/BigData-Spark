package com.tian.onclass.day04.high

import scala.collection.mutable

/**
 * WordCount
 *
 * @author tian
 *         2019/9/7 15:45
 */
object FoldLeftDemo2 {
    def main(args: Array[String]): Unit = {
        val list1 = List("Hello World", "hello World", "tian tian", "hello")
        val words = list1.flatMap(_.split(" "))
        val reduce1 = words.foldLeft(Map[String, Int]())((map, word) => {
            map + (word -> (map.getOrElse(word, 0) + 1))
        })
        val reduce2 = words.foldLeft(mutable.Map[String, Int]())((map, word) => {
            map + (word -> (map.getOrElse(word, 0) + 1))
        })
        println(reduce1)
        println(reduce2)
    }
}
