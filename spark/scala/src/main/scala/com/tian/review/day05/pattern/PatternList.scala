package com.tian.review.day05.pattern

/**
 * 匹配List
 *
 * @author tian
 *         2019/9/10 8:26
 */
object PatternList {
    def main(args: Array[String]): Unit = {
        val list = List(10, 20, 30, 40)
        list match {
            case List(_, a, _, _) => println(a)
            case List(a, _*) => println(a)
            case List(_, a@_*) => println(a)
            case a :: b :: c => println(c) //List专用，c为集合(30,40)
            case a :: b :: c :: Nil => println(c)
            case _ =>
        }
    }
}
