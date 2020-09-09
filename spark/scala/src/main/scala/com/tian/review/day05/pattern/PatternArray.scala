package com.tian.review.day05.pattern

/**
 *
 * @author Friday
 * @version 1.0
 *          2019/9/9 23:43
 */
object PatternArray {
    def main(args: Array[String]): Unit = {
        val arr = Array(10, 20, 30, 50)
        arr match {
            case Array(10, 20, 30, 50) => println("same") //匹配元素个数，元素值和顺序
            case Array(_, _, _, _) => println("num") //匹配元素个数
            case Array(10, _, _, _) => println("first and num") //匹配第一个元素内容和元素个数
            case Array(10, a, _, _) => println(s"first = 10 and second = $a") //后续还要继续使用元素时
            //case Array(10, a: Double, _, _) => println("error") //类型不匹配编译检查不通过
            case Array(10, _*) => println("first = 10") //只匹配第一个，其余忽略
            case Array(10, a@_*) => println(s"first = 10 and a = $a") //只匹配第一个，后面的元素还要使用
        }
    }
}
