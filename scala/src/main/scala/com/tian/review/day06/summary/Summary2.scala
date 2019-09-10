package com.tian.review.day06.summary

/**
 * either
 *
 * @author tian
 *         2019/9/10 21:12
 */
object Summary2 {
    def main(args: Array[String]): Unit = {
        val num = fun(100)
        //获取结果
        if (num.isRight) println(num.right.get)
        else println(num.left.get)
        //通过模式匹配获取结果
        num match {
            case Left(a) => println(a)
            case Right(a) => println(a)
        }

    }

    def fun(a: Int) =
        if (a < 0) Left("illegal argument")
        else Right(math.sqrt(a))
}
