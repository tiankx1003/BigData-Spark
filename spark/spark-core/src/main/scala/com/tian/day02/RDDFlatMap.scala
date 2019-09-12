package com.tian.day02

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 *         2019/9/12 11:24
 */
object RDDFlatMap {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array("tian test","test scala","scala test","spark scala"))
        val rdd2 = rdd1.flatMap(_.split("\\W+"))
        rdd2.collect.foreach(println)
        sc.stop()
    }
}
