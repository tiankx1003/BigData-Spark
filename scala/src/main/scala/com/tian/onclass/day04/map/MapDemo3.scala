package com.tian.onclass.day04.map

import scala.collection.mutable

/**
 * Map操作
 *
 * @author tian
 *         2019/9/7 11:41
 */
object MapDemo3 {
    def main(args: Array[String]): Unit = {
        //可变
        val map = mutable.Map("a" -> 97, "b" -> 98, "c" -> 99, "d" -> 100)
        //添加kv
        //        map  += (("z",102))
        map += "z" -> 102
        println(map)
        //不可变
        var map2 = Map("a" -> 97, "b" -> 98, "c" -> 99, "d" -> 100)
        println(map2)
        println(System.identityHashCode(map2))
        map2 += "z" -> 102 //其实是添加赋给一个新的map
        println(map2)
        //打印地址验证
        println(System.identityHashCode(map2))

        map.getOrElseUpdate("x",200) //不存在时直接更新一个，只能在可变使用
    }
}

/**
 * +=
 * 可变的更改原集合
 * 不可变生成一个新集合
 */
