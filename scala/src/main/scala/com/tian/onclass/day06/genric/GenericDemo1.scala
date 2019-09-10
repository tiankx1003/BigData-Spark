package com.tian.onclass.day06.genric

/**
 * 泛型
 *
 * @author tian
 *         2019/9/10 10:28
 */
/*
泛型:类型的参数化
泛型类:把参数化的类型定义在类上，这个泛型在整个类都可以使用
泛型方法:把泛型定义在方法上，这个泛型只能在这个方法内使用(当数据的类型来用)
 */
object GenericDemo1 {
    def main(args: Array[String]): Unit = {
        val point1 = new Point[Int](10, 20)
        val point2 = new Point(10, 20)
        //new Point[Int](10,20.1) //编译报错
    }
}

/**
 * 泛型类
 * @param x
 * @param y
 * @tparam T
 */
class Point[T](val x: T, val y: T) {
    //val a:T = 10 //不能直接赋值
    def f0(a: T) = a //在具体使用时赋值
}

class User {
    /**
     * 泛型方法
     *
     * @param x
     * @param y
     * @tparam T
     */
    def f0[T](x: T, y: T) = println(x + "" + y)
}
