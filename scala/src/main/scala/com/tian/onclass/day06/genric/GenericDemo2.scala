package com.tian.onclass.day06.genric

/**
 * 泛型的上下界
 *
 * @author tian
 *         2019/9/10 10:38
 */
/*
scala的泛型界定
上界

下界


上下文界定


视图界定(过时)
 */
object GenericDemo2 {
    def main(args: Array[String]): Unit = {
        println(max("a", "b"))
        println(max(Users(10), Users(20)))
    }

    /**
     * 通过设置上限来继承compareTo方法
     *
     * @param first
     * @param second
     * @tparam T
     * @return
     */
    def max[T <: Comparable[T]](first: T, second: T): T =
        if (first.compareTo(second) >= 0) first else second

    /**
     * 设置上限继承Ordered，直接可以使用运算符比较
     *
     * @param first
     * @param second
     * @tparam T
     * @return
     */
    def max1[T <: Ordered[T]](first: T, second: T) =
        if (first > second) first else second
}

/**
 * 样例类继承Ordered重写compare方法实现比较规则
 *
 * @param age
 */
case class Users(val age: Int) extends Ordered[Users] {
    override def compare(that: Users): Int = this.age - that.age
}