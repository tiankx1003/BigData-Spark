package com.tian.day02

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 *         2019/9/12 13:57
 */
object RDDSample {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array(20, 30, 40, 50))
        //def sample(withReplacement: Boolean, fraction: Double)
        //withReplacement表示是否放回，fraction表示抽取比例
        val rdd2 = rdd1.sample(false, 0.5)
        rdd2.collect.foreach(println)
        sc.stop()
    }
}
