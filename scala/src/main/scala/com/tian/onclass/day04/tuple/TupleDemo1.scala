package com.tian.onclass.day04.tuple

/**
 * @author tian
 *         2019/9/7 10:41
 */
/*
Tuple 元组

 */
object TupleDemo1 {
    def main(args: Array[String]): Unit = {
        val t2 = Tuple2(10, "abc")
        println(t2._1) //索引从1开始
        println(t2._2)
        //Tuple最多存22个
        val t3 = (1, "abc", 123L, 5555.555F)
        println(t3)
        println(t3._1)
        for (elem <- t3.productIterator) { //遍历元组(意义不大)，生成迭代器
            println(elem)
        }
        println(/%(10, 3))
    }

    /**
     * 同时返回商和余数
     *
     * @param a 被除数
     * @param b 除数
     * @return 返回一个元组包含商和余数
     */
    def /%(a: Int, b: Int) = {
        (a / b, a % b)
    }
}
