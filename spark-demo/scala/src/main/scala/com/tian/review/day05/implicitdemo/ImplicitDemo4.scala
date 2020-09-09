package com.tian.review.day05.implicitdemo

import scala.io.Source
import java.io.File

/**
 * File直接读取文件内容
 *
 * @author Friday
 * @version 1.0
 *          2019/9/9 23:00
 */
object ImplicitDemo4 {
    def main(args: Array[String]): Unit = {
        val content: String = new File("e:\\test.txt").readContent //添加文件路径
        println(content)
    }

    implicit class RichFile(file: File) {
        def readContent() = Source.fromFile(file).mkString
    }

}
