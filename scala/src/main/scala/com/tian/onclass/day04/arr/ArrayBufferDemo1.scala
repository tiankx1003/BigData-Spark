package com.tian.onclass.day04.arr

import scala.collection.mutable.ArrayBuffer

/**
 * @author tian
 *         2019/9/7 9:27
 */
object ArrayBufferDemo1{
    def main(args: Array[String]): Unit = {
        val buffer = new ArrayBuffer[Int]
        buffer.append(10,20) //append方法有可变形参
        buffer += 100 // +=一般用于可变集合
        buffer += 10
        buffer += (20,200)
        buffer -= 11 //删除第一个11
        buffer.insert(3,20) //insert(index,elem)
        buffer.remove(0) //删除指定索引位置的元素
        buffer.remove(0,2) //从索引0开始删除2个元素
        println(buffer)
    }
}
