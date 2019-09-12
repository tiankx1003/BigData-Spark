package com.tian.day02

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 *         2019/9/12 14:06
 */
object RDDDistinct {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array(20, 30, 40, 50, 20, 50))
        //def distinct(numPartitions: Int)(implicit ord: Ordering[T] = null)
        val rdd2 = rdd1.distinct() //不传参时使用集合长度distinct(partitions.length)
        val rdd3 = rdd1.distinct(4) //自定义类需要提供Ordering类型隐式值
        rdd2.collect.foreach(println)
        sc.stop()
    }
}
