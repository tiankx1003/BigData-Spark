package com.tian.day02.singlevalue

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 *         2019/9/12 11:35
 */
object RDDFilter {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array(20, 30, 40, 50))
        val rdd2 = rdd1.filter(_ % 2 == 0)
        val rdd3 = rdd1.filter(x => (x & 1) == 1)
        rdd2.collect.foreach(println)
        sc.stop()
    }
}
