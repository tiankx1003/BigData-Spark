package com.tian.review.day04.tuple

/**
 * @author tian
 *         2019/9/11 20:37
 */
object TupleDemo {
    def main(args: Array[String]): Unit = {
        val t2 = Tuple2(10, "abc")
        println(t2._1) //索引从1开始
        println(t2._2) //tuple最多存放22个元素
        val t5 = (1, "abc", 123L, 5555.555F)
        //先获取迭代器再遍历
        for (elem <- t5.productIterator) println(elem)
        println(/%(10, 3))
    }

    /**
     * 同时返回两数的商和余数
     *
     * @param a 被除数
     * @param b 除数
     * @return (Int, Int) 返回一个元组包含商和余数
     */
    def /%(a: Int, b: Int) = (a / b, a % b)
}
