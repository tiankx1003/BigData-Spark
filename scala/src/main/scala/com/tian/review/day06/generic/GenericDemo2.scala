package com.tian.review.day06.generic

/**
 * 泛型上界
 *
 * @author tian
 *         2019/9/10 18:51
 */
object GenericDemo2 {
    def main(args: Array[String]): Unit = {

    }

    /**
     * 通过设置泛型上界从而继承compareTo方法
     *
     * @param a
     * @param b
     * @tparam T
     * @return
     */
    def max[T <: Comparable[T]](a: T, b: T) =
        if (a.compareTo(b) >= 0) a else b

    /**
     * 通过设置上限继承Ordered中的方法，可以直接使用运算符比较大小
     *
     * @param a
     * @param b
     * @tparam T
     * @return
     */
    def min[T <: Ordered[T]](a: T, b: T) =
        if (a > b) b else a
}

/**
 * 样例类继承Ordered类并重写compare方法实现比价规则
 *
 * @param age
 */
case class User(val age: Int) extends Ordered[User] {
    override def compare(that: User): Int = this.age - that.age
}
