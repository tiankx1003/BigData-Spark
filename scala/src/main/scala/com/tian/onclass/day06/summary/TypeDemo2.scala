package com.tian.onclass.day06.summary

/**
 * @author tian
 *         2019/9/10 14:17
 */
/*
either
    用于表示正确或者错误的结果
 */
object TypeDemo2 {
    def main(args: Array[String]): Unit = {
        val e = f0(100)
        if (e.isRight) println(e.right.get)
        else println(e.left.get)
        //使用模式匹配的方式获取值
        f0(200) match {
            case Left(v) => println(v)
            case Right(v) => println(v)
        }
    }

    def f0(a: Double): Either[String, Double] = {
        if (a < 0)
            Left("参数不合法")
        else
            Right(math.sqrt(a))
    }
}
