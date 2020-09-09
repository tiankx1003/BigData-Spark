package com.tian.day03.actition

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/16 16:13
 * @version 1.0.0
 */
object Reduce {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array(3, 4, 0, 12, 9, 2))
        println(rdd1.reduce(_ + _))
        //zeroValue参与运算的次数是分区数+1
        val result1 = rdd1.aggregate(0)(_ + _, _ + _)
        val result2 = rdd1.aggregate(10)(_ + _, _ + _) //分区合并也会使用零值
        println(result2 - result1) //30
    }
}
