package com.tian.day02.singlevalue

import org.apache.spark.{SparkConf, SparkContext}

/**
 * map算子
 *
 * @author tian
 *         2019/9/12 10:06
 */
object RDDMap {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array(20, 30, 40, 50))
        val rdd2 = rdd1.map(x => x * x)
        rdd2.collect.foreach(println)
        sc.stop()
    }
}
