package com.tian.day02

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 *         2019/9/12 10:20
 */
object RDDMapPartitions {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array(20, 30, 40, 50))
        //和map的区别是传入一个分区的迭代器，然后对迭代器还是做map操作
        //mapPartition是一个分区执行一次，map是一个元素执行一次
        //transformation是在executor上执行，之前的操作都是在driver执行
        //数据量特别大时小心OOM，因为每个分区的数据都是会放入memory的
        //数据量大时考虑使用map而不是mapPartitions
        val rdd2 = rdd1.mapPartitions(it => it.map(x => x * x))
        //collect是就是把executor转换后的rdd拉取到driver
        rdd2.collect.foreach(println)
    }
}
