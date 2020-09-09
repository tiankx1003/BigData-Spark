package com.tian.review.day04.set

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
 * @author tian
 *         2019/9/12 8:19
 */
object SetDemo {
    def main(args: Array[String]): Unit = {
        var set1 = Set(10, 20, 30, 40, 40) //Set会自动去重
        set1 += 10
        set1 -= 10
        set1 ++= Array(20, 30, 40, 50)
        set1 ++= List(10, 20, 30)
        set1 ++= ListBuffer(10, 20, 40)
        var set2 = Set(10, 20, 30)
        set2 += 120
        set2 += 2220
        set2 += 100

        //可变Set
        val set3 = mutable.Set(10, 20, 30, 40)

        def toDuplicate(list: List[Int]) = {
            (Set[Int]() ++ list).toList //使用空Set添加元素自动去重，然后再转为List
            list.toSet.toList //简写
        }

        /*
        Set操作
         */
        //并集
        set1 ++ set2
        //def union(that: GenSet[A]): This = this ++ that
        set1.union(set2) //底层仍旧是 ++
        set1 | set2
        //交集
        set1 & set2
        set1.intersect(set2)
        //差集
        set1 &~ set2
        set1 -- set2
        set1.diff(set2)
    }
}
