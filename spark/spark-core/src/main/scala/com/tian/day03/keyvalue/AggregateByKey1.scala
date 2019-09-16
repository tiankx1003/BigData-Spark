package com.tian.day03.keyvalue

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/16 10:17
 * @version 1.0.0
 */
object AggregateByKey1 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array("hello", "hello", "world", "hello", "tian", "hello"))
        val rdd2 = rdd1.map((_, 1)) //变为kv形式
        //def aggregateByKey[U: ClassTag](zeroValue: U)(seqOp: (U, V) => U, combOp: (U, U) => U): RDD[(K, U)]
        //零值类似foldLeft，第一个函数为预聚合逻辑(分区内聚合操作)，第二个函数为分区间聚合操作
        val rdd3 = rdd2.aggregateByKey(0)(_ + _, _ + _) //两次聚合逻辑相同
        rdd3.collect.foreach(println)
        sc.stop()
    }
}
