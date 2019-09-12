package com.tian.day02

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 *         2019/9/12 11:38
 */
object RDDGroupBy {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array(20, 30, 40, 50), 2)
        //有可选分区器和shuffle过程
        //groupBy没有预聚合(类似于MR中combiner)
        val rdd2 = rdd1
            .groupBy(_ % 2 == 1)
            .map {
                case (k, iterator) => (k, iterator.sum)
            }

        rdd2.collect.foreach(println)
        sc.stop()
    }
}
