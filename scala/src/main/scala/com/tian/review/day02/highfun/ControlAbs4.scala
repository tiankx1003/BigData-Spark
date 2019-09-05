package com.tian.review.day02.highfun

/**
 * 结合名调用、递归和柯里化写一个类似于while的语法结构
 *
 * @author tian
 *         2019/9/5 10:26
 */
object ControlAbs4 {
    def main(args: Array[String]): Unit = {
        var a = 10
        var b = 10
        while2(a > 0) {
            println(a)
            a -= 1
        }
        while (b > 0) {
            println(b)
            b -= 1
        }
    }

    def while2(boo: Boolean)(f: => Unit): Unit = {
        if (boo) {
            f
            while2(boo)(f) //通过递归完成循环
        }
    }
}
