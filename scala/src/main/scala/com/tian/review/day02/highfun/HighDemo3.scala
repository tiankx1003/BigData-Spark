package com.tian.review.day02.highfun

/**
 * 通过高阶函数实现map reduce filter
 *
 * @author tian
 *         2019/9/4 20:51
 */
object HighDemo3 {
    def main(args: Array[String]): Unit = {
        val arr1 = map[Int, String](Array(1, 2, 3, 4), _ + "a")
        val arr2 = map[Int, Int](Array(1, 2, 3, 4), _ + 1)
        val arr3 = map[Char, Int](Array('a', 'b', 'c', 'd'), _.toInt)
        val num1 = reduce[Int](Array(1, 2, 3, 4), _ + _) // 求和
        val str1 = reduce[String](Array("aa", "bb", "cc", "dd"), _ + _) // 拼接
        val arr4 = filter[Int](Array(1, 2, 3, 4), _ % 2 == 0) // 判断每一位是不是偶数
    }

    /**
     * map端对一个数组的每个元素进行操作
     *
     * @param arr 传入的数组
     * @param op  操作函数
     * @tparam T 传入数组的泛型
     * @tparam S 传出数组的泛型
     * @return 经过op操作后得到的数组
     */
    def map[T, S](arr: Array[T], op: T => S) = {
        for (i <- arr) yield op(i) //迭代，使每个元素传入函数
    }

    /**
     * 聚合
     * 使用操作函数对每个元素和上次操作结果(迭代)
     *
     * @param arr 传入的数组
     * @param op  操作函数
     * @tparam T 传入数组的泛型，
     *           tparam S 操作函数返回的泛型
     */
    // TODO 泛型有问题，待解决
    // def reduce[T, S](arr: Array[T], op: (S, T) => S) = {
    def reduce[T](arr: Array[T], op: (T, T) => T) = {
        var init = arr(0)
        for (index <- 1 until arr.length) op(init, arr(index)) //递归
        init
    }

    /**
     * 迭代数组的元素，使每个传入迭代函数
     *
     * @param arr 传入数组
     * @param op  过滤函数
     * @tparam T 数组泛型
     */
    def filter[T](arr: Array[T], op: T => Boolean) = for (n <- arr) yield op(n)
}
