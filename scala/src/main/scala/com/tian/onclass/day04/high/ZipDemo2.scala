package com.tian.onclass.day04.high

/**
 * @author tian
 *         2019/9/7 16:29
 */
object ZipDemo2 {
    def main(args: Array[String]): Unit = {
        val t = List((10,1),(20,2),(20,3)) //二维元组又称对偶
        println(t.unzip)
        val t3 = List((10,20,30),(1,2,3),(120,20,30))
        println(t3.unzip3)
    }
}
