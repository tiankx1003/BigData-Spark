package com.tian.onclass.day04.arr

import scala.collection.mutable.ArrayBuffer

/**
 * ArrayBuffer运算符
 *
 * @author tian
 *         2019/9/7 10:14
 */
object ArrayBufferDemo3 {
    def main(args: Array[String]): Unit = {
        val buffer1 = ArrayBuffer[Int](10, 20, 30)
        val buffer2 = ArrayBuffer[Int](100, 200, 300)
        val b3 = buffer1 ++ buffer2 //buffer1.++(buffer2)
        //        val b4 = buffer1 ++: buffer2 //buffer2.++:(buffer1) TODO ?
        buffer1 ++= buffer2
        println(buffer1)
        println(buffer2)
        buffer1 --= buffer2 //减去公共部分
    }
}
