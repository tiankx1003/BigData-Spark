package com.tian.review.day06.summary

/**
 * Option
 *
 * @author tian
 *         2019/9/10 21:05
 */
class Summary1 {
    def main(args: Array[String]): Unit = {
        val f = f0()
        println(f.get)
        println(f.getOrElse(100)) //防止空值

        f match { //通过模式匹配解决空值问题
            case Some(a) => println(a)
            case None =>
        }
    }

    def f0(): Option[Int] = {
        Some(10)
        None
    }
}
