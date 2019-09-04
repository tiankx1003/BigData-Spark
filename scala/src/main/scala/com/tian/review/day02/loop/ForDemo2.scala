package com.tian.review.day02.loop
import scala.collection.immutable

/**
 * @author tian
 *         2019/9/4 18:48
 */
object ForDemo2 {
    def main(args: Array[String]): Unit = {
        //使用for推导输出序列中每个数的三次方
        val arr: immutable.IndexedSeq[Int] = for(i <- 1 to 5) yield i*i*i
        println(arr)
        //序列中的每个元素加3
        println((1 to 4).map(_ + 3))
    }
}
