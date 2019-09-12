package com.tian.day02

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 *         2019/9/12 10:32
 */
object RDDMapPartitionsWithIndex {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array(20, 30, 40, 50), 4) //切片数和分区规则
        val rdd2 = rdd1.mapPartitionsWithIndex((index, it) => it.map((index, _)))
        println(rdd2.collect.mkString(","))
        sc.stop()
    }
}
