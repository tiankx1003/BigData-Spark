package com.tian.onclass.day04.arr

/**
 * 多维数组
 *
 * @author tian
 *         2019/9/7 10:27
 */
object ArrayDemo2 {
    val array = Array.ofDim[Int](2, 3)
    for (arr <- array) {
        for (elem <- arr) {
            println(elem)
        }
    }
}
