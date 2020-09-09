package com.tian.review.day06.generic

/**
 * @author tian
 *         2019/9/10 19:19
 */
object GenericDemo4 {
    //提供隐式值
    implicit val ord: Ordering[Users] = new Ordering[Users] {
        override def compare(x: Users, y: Users): Int = x.age - y.age
    }

    def main(args: Array[String]): Unit = {
        f1(10, 20) //调用隐式值
        f1(10, 20)(Ordering.Int)
        f2("aa", "bb")
        f2("aa", "bb")(Ordering.String)
        println(f2(new Users(20), new Users(30)))
        println("Hello World")
    }

    /**
     * 柯里化、隐式值
     *
     * @param a
     * @param b
     * @param ord
     * @tparam T
     * @return
     */
    def f1[T](a: T, b: T)(implicit ord: Ordering[T]) =
        if (ord.gt(a, b)) a else b

    /**
     * 上下文界定
     * 召唤隐式值Ordering[T]
     *
     * @param a
     * @param b
     * @tparam T
     * @return
     */
    def f2[T: Ordering](a: T, b: T) = {
        //summoning implicit values from the nether world
        val ord = implicitly[Ordering[T]] //召唤隐式值
        if (ord.gt(a, b)) a else b
    }
}

case class Users(val age: Int)
