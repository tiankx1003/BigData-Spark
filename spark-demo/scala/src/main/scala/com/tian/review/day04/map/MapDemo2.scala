package com.tian.review.day04.map

import scala.collection.mutable

/**
 * 可变Map
 *
 * @author tian
 *         2019/9/11 21:25
 */
object MapDemo2 {
    def main(args: Array[String]): Unit = {
        val map = mutable.Map((1,10),(2,20),(3,30))
        println(map)
    }
}
