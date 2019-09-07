package com.tian.onclass.day04.arr

import scala.collection.mutable.ArrayBuffer

/**
 * @author tian
 *         2019/9/7 10:01
 */
object ArrayBufferDemo2 {
    def main(args: Array[String]): Unit = {
        val buffer = ArrayBuffer[Int](10,20,30)
        val buffer1 = buffer :+ 10 //得到一个新的数组
        val buffer2 = buffer.+:(100) //
        val buffer3 = 100 +: buffer //同buffer2
        val buffer4 = 400 +: 300 +: buffer
        //冒号在集合的一侧
        println(buffer)
        println(buffer1)
    }
}
/*
+=
-=
:+
+:

运算符的约定
    两种结合性
        左结合 3+4  3*5
        右结合 -5  a=3
        如果一个运算符以冒号结尾，则也是右结合
冒号改变了结合的结合性
 */
