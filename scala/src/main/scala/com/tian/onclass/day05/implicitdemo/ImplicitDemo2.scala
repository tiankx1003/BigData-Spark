package com.tian.onclass.day05.implicitdemo

import java.io.File

import scala.io.Source

/**
 * 隐式转换为File类增加功能(直接读取文件内容)
 *
 * @author tian
 *         2019/9/9 11:29
 */
object ImplicitDemo2 {
    def main(args: Array[String]): Unit = {
        implicit def file3RichFile(file: File) = new RichFile(file)

        val content: String = new File("").readContent

    }

}

class RichFile(file: File) {
    def readContent = Source.fromFile(file).mkString
}