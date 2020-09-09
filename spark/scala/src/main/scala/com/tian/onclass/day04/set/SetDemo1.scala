package com.tian.onclass.day04.set

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
 * set
 *
 * @author tian
 *         2019/9/7 13:59
 */
/*
set
无序不可重复
scala中Set长度超过4时会优化顺序
set多用于去重
 */
object SetDemo1 {
    def main(args: Array[String]): Unit = {
        var set1 = Set(10,20,200,20,30) //自动去重
        set1 += 10 //
        set1 -= 10
        set1 ++= Array(20,30,40,50)
        set1 ++= List(10,20,30)
        set1 ++= ListBuffer(10,20,40)
        println(set1)
        var set2 = Set(10,30,20)
        set2 += 120
        set2 += 255
        set2 += 100

        //可变
        var set3 = mutable. Set(10,20,200,20,30)

        /*
        判断对象是不是相同的步骤:判断hash 判断是不是同一个对象 equals
         */
        def toDuplicate(list:List[Int]) = {
            //(Set[Int]() ++ list).toList //使用空Set添加list中的元素去重并转换返回值为List
            list.toSet.toList //更简单的写法
        }

    }
}

