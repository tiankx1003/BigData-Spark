package com.tian.day03.keyvalue

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/16 14:15
 * @version 1.0.0
 */
object Cogroup {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array((1, "a"), (2, "b"), (3, "c")))
        val rdd2 = sc.parallelize(Array((1, "A"), (2, "B"), (3, "C")))
        val rdd3 = rdd1.cogroup(rdd2)
        rdd3.collect.foreach(println)
        sc.stop()
    }
}
