package com.tian.onclass.day05.onclass.pattern

/**
 * 数组内容匹配
 *
 * @author tian
 *         2019/9/9 15:31
 */
object PatternArray {
    def main(args: Array[String]): Unit = {
        val arr = Array(10, 20, 30)

        arr match {
            case Array(10, 20, 30) => println("1") //同时匹配数量，元素，顺序
            case Array(10, _, _) => println("2") //匹配长度和首个元素
            case Array(10, a, _) => println(a) //匹配后使用值
            //case Array(10, a: Double, _) => println(a) //和Array类型不匹配，编译检查不通过
            case Array(10, _*) => println("3") //只匹配第一个，不匹配长度
            case Array(10, a@_*) => println(a.mkString(",")) //a是一个数组
            case _ =>
        }
    }
}
