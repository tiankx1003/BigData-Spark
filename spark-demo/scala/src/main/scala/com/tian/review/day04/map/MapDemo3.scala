package com.tian.review.day04.map

import scala.collection.mutable

/**
 * @author tian
 *         2019/9/12 8:12
 */
object MapDemo3 {
    def main(args: Array[String]): Unit = {
        val map1 = mutable.Map("a" -> 97, "b" -> 98, "c" -> 99)
        map1 += (("z", 102))
        map1 += "y" -> 101
        map1.getOrElseUpdate("x",200) //不存在时直接更新出一个
        var map2 = Map("a" -> 97, "b" -> 98,"c" -> 99)
        println(System.identityHashCode(map2)) //打印地址
        map2 += "z" -> 102 //本质是添加赋给一个新的map
        println(System.identityHashCode(map2)) //与前者对比验证
    }
}
