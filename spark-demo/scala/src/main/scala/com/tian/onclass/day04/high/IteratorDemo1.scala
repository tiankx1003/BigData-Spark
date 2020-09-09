package com.tian.onclass.day04.high

/**
 * @author tian
 *         2019/9/7 16:38
 */
object IteratorDemo1 {
    def main(args: Array[String]): Unit = {
        val it = "abasasd".toIterator
        it.foreach(println(_)) //迭代器只能使用一次
        it.foreach(println(_)) // 空
        val it2 = "abasasd".toIterator.toList
        it2.foreach(println(_))
        it2.foreach(println(_)) //转成list后可以多次使用，但是数据量不断增多，会内存溢出，还是要求只迭代一次
        val it3 = "sdasd".toIterator
        it3.foreach(println)

    }

}
