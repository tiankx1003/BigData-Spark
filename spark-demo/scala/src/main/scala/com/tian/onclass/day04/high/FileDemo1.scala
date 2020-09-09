package com.tian.onclass.day04.high

import scala.io.Source

/**
 * @author tian
 *         2019/9/7 17:17
 */
object FileDemo1 {
    def main(args: Array[String]): Unit = {
        val path = "C:..." //文件路径
        val lines = Source.fromFile(path).getLines().toList
    }
}
