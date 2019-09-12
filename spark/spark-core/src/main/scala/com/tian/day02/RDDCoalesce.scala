package com.tian.day02

import org.apache.spark.{SparkConf, SparkContext}

/**
 * coalesce
 * 用于减少分区数
 * 且这个减少分区，不会shuffle
 *
 * @author tian
 *         2019/9/12 14:10
 */
object RDDCoalesce {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array(20, 30, 40, 50, 20, 50), 4)
        val rdd2 = rdd1.coalesce(2)
        println(rdd1.getNumPartitions)
        println(rdd2.getNumPartitions)
        val rdd3 = rdd1.coalesce(10,true) //shuffle为true并增加
        sc.stop()
    }
}
/*
一个父分出多个子就是宽依赖
宽依赖必定shuffle
 */
