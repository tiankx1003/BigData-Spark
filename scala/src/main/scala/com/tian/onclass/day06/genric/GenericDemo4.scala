package com.tian.onclass.day06.genric

/**
 * 上下文界定
 * T : M
 * 必须一定会有一个隐式值 M[T]
 *
 * @author tian
 *         2019/9/10 11:22
 */
object GenericDemo4 {
    def main(args: Array[String]): Unit = {
        f0(1, 2) //使用隐式值
        f0(1, 2)(Ordering.Int)
        f1(1, 2)
        f1(1, 2)(Ordering.Int)
        f1("a", "b")(Ordering.String)
//        f1(new Custom(20), new Custom(30)) //TODO 需要自定义类提供隐式值
    }

    def f0[T](a: T, b: T)(implicit ord: Ordering[T]) = if (ord.gt(a, b)) a else b

    /**
     *
     * @param a
     * @param b
     * @tparam T
     * @return
     */
    def f1[T: Ordering](a: T, b: T) = {
        //for summoning implicit values from the nether world
        val ord = implicitly[Ordering[T]] //召唤隐式值
        if (ord.gt(a, b)) a else b
    }
}

//TODO 提供隐式值
class Custom(val age: Int)
