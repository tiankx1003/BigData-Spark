package com.tian.review.day04.arr

/**
 * 多维数组
 *
 * @author tian
 *         2019/9/11 20:01
 */
object ArrayDemo2 {
    def main(args: Array[String]): Unit = {
        //scala和java相同，二维数组的元素即为一维数组
        val arr0 = Array.ofDim[Int](2, 3)
        for (arr <- arr0)
            for (elem <- arr)
                println(elem)
    }
}
