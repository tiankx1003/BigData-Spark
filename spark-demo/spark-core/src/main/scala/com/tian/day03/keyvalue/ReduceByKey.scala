package com.tian.day03.keyvalue

import org.apache.spark.{RangePartitioner, SparkConf, SparkContext}

/**
 * reduceByKey
 * 把key相同的value放在一起进行reduce
 * 默认使用Hash分区
 * 可以传入分区数
 * 会有预聚合操作，然后再shuffle，提升了性能
 * groupByKey只是分区，之后就shuffle
 * 业务允许时优先使用reduceByKey
 * 要求预聚合的逻辑和最终聚合逻辑一致
 *
 * @author tian
 * @date 2019/9/16 9:57
 * @version 1.0.0
 */
object ReduceByKey {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array("hello", "hello", "world", "hello", "tian", "hello"))
        val rdd2 = rdd1.map((_, 1)) //变为kv形式
        // TODO shuffle 预聚合
        val rdd3 = rdd2.reduceByKey(_ + _) //wordCount
        val rdd4 = rdd2.reduceByKey(_ + _, 3) //传入分区数，默认使用hash分区
        rdd3.collect.foreach(println)
        sc.stop()
    }
}
