package com.tian.day02.singlevalue

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 改变分区数，从而改变并行数，可增可减，但是不推荐使用repartition减少分区
 *
 * @author tian
 *         2019/9/12 14:18
 */
object RDDRepartition {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array(20, 30, 40, 50, 20, 50), 4)
        //coalesce(numPartitions, shuffle = true)，本质上调用coalesce
        val rdd2 = rdd1.repartition(10)
        sc.stop()
    }
}
