package com.tian.day03.keyvalue

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 如果分区内和分区间聚合逻辑相同可使用foldByKey替换aggregateByKey
 * 如果又不需要零值，则可以使用reduceByKey替换foldByKey
 *
 * @author tian
 * @date 2019/9/16 11:18
 * @version 1.0.0
 */
object FoldByKey {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array("hello", "hello", "world", "hello", "tian", "hello"))
        val rdd2 = rdd1.map((_, 1)) //变为kv形式
        val rdd3 = rdd2.aggregateByKey(0)(_ + _, _ + _) //两次聚合逻辑相同
        val rdd4 = rdd2.foldByKey(0)(_ + _) //效果同上
        rdd3.collect.foreach(println)
        sc.stop()
    }
}
