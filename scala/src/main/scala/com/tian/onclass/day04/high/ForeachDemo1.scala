package com.tian.onclass.day04.high

/**
 * @author tian
 *         2019/9/7 14:19
 */
object ForeachDemo1 {
    def main(args: Array[String]): Unit = {
        val list1 = List(20,40,30,40,50)
        //@inline final override def foreach[U](f: A => U)
        list1.foreach(println(_))
        "aaabbbccc".foreach(println(_))
    }
}
