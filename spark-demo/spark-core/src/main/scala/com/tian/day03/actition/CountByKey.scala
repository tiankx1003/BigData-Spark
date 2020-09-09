package com.tian.day03.actition

import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author tian
 * @date 2019/9/16 15:22
 * @version 1.0.0
 */
object CountByKey {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array("hello", "hello", "world", "scala"))
        val rdd2 = rdd1.map(x => {
            println(x)
            (x, 1)
        }) //转换算子在行动算子之前不会被执行
        val rdd3 = rdd2.reduceByKey(_ + _)
        val rdd4 = rdd2.countByKey() //计算每个key个数，和value值无关
        val rdd5 = rdd2.countByValue() //计算每个value个数
        sc.stop()
    }
}
