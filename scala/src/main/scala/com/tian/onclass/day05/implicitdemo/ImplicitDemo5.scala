package com.tian.onclass.day05.implicitdemo

/**
 * @author tian
 *         2019/9/9 13:54
 */
object ImplicitDemo5 {

    def main(args: Array[String]): Unit = {
        foo(10)
        implicit val a = 10
        //        implicit val b = 20 //有多个时报错
        foo //使用隐式值，自动寻找implicit修饰的，类型符合要求的值
        foo() //使用默认值
        foo1(300) //

        println("".sortBy(x => x)(Ordering.Char.reverse))
    }

    def foo(implicit a: Int = 100) = {
        println(a)
    }

    /**
     * 柯里化和隐式值的配合使用
     *
     * @param n
     * @param a
     */
    def foo1(n: Int)(implicit a: Int) = {
        println(n + a)
    }
}
