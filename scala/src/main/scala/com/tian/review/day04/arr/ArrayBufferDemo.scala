package com.tian.review.day04.arr

import scala.collection.mutable.ArrayBuffer

/**
 * ArrayBuffer
 *
 * @author tian
 *         2019/9/11 20:05
 */
object ArrayBufferDemo {
    def main(args: Array[String]): Unit = {
        val arr1 = new ArrayBuffer[Int]
        //def append(elems: A*) { appendAll(elems) }
        arr1.append(10, 20) //append方法可变形参
        arr1 += 100 //+=一般用于可变集合
        arr1 += (20, 320)
        arr1 -= 11 //删除第一个11
        //def insert(n: Int, elems: A*) { insertAll(n, elems) }
        arr1.insert(3, 20)
        arr1.remove(0) //删除指定索引位置的元素
        arr1.remove(0, 2) //从索引0开始删除2个元素
        val sum = arr1.sum
        val max = arr1.max
        val min = arr1.min
        val head = arr1.head //第一个元素
        val last = arr1.last //最后一个元素
        val tail = arr1.tail //去除第一个元素后剩余元素组成的结合
        val take2 = arr1.take(2) //取前两个元素组成的集合
        //有些集合没有索引

        //运算符的约定，冒号改变了结合性
        val arr2 = ArrayBuffer[Int](10, 20, 30)
        val buffer1 = arr2 :+ 10
        val buffer2 = arr2.+:(10)
        val buffer3 = 100 +: arr2
        val buffer4 = 400 +: 300 +: arr2

        val buffer5 = arr1 ++ arr2
        arr1 ++= arr2
        arr1 --= arr2 //减去公共部分

        for (elem <- arr2 if elem > 5) println(elem) //遍历与守卫
    }
}
