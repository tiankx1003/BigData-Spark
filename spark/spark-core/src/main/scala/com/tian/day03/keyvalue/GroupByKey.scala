package com.tian.day03.keyvalue

import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author tian
 * @date 2019/9/16 10:08
 * @version 1.0.0
 */
object GroupByKey {
    def main(args: Array[String]): Unit = {
        // TODO Live Templates
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array("hello", "hello", "world", "hello", "tian", "hello"))
        val rdd2 = rdd1.map((_, 1)).groupByKey //变为kv形式
        val rdd3 = rdd2.map {
            case (key, valueIt) => (key, valueIt.sum)
        }
        rdd2.collect.foreach(println)
        println(".........")
        rdd3.collect.foreach(println)
        sc.stop()
    }
}
