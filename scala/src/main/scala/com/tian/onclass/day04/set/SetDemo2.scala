package com.tian.onclass.day04.set

/**
 * @author tian
 *         2019/9/7 14:10
 */
object SetDemo2 {
    def main(args: Array[String]): Unit = {
        val set1 = Set(10,20,30,40)
        val set2 = Set(10,11,12,13)
        //并集
        println(set1 ++ set2)
        println(set1.union(set2)) //底层还是++
        println(set1 | set2)
        //交集
        println(set1 & set2)
        println(set1.intersect(set2))
        //差集
        println(set1 &~ set2)
        println(set1 -- set2)
        println(set1.diff(set2))
    }
}
