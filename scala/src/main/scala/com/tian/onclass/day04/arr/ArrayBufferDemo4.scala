package com.tian.onclass.day04.arr

import scala.collection.mutable.ArrayBuffer

/**
 * @author tian
 *         2019/9/7 10:21
 */
object ArrayBufferDemo4 {
    def main(args: Array[String]): Unit = {
        val buffer1 = ArrayBuffer[Int](10,320,30,40)
        for (elem <- buffer1 if elem < 5000) { //遍历与守卫
            println(elem)
        }
        //API
        println(buffer1.sum)
        println(buffer1.max)
        println(buffer1.min)
        println(buffer1.head)//第一个元素
        println(buffer1(0)) //有些集合没有索引
        println(buffer1.last)//最后一个元素
        println(buffer1.tail)//去除第一个元素剩下的元素组成的集合
        println(buffer1.take(2))//取前两个元素组成的集合
    }
}
