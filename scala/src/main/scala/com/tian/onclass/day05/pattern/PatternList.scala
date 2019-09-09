package com.tian.onclass.day05.pattern

/**
 * @author tian
 *         2019/9/9 15:45
 */
/*
中置表达式
1 + 2 中置运算符+
+2 前置运算符
a toString 后置
 */
object PatternList {
    def main(args: Array[String]): Unit = {
        val list = List(1, 2, 3, 4)
        list match {
            case List(_, a, _, _) => println(a)
            case List(a, _*) => println(a)
            case List(a, b@_*) => println(b)
            case a :: b :: c => println((a, b, c)) //List专用，c为(3,4)这个集合，
            case a :: b :: c :: Nil => println((a, b, c))
            case _ =>
        }
    }
}
