package com.tian.day02

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 *         2019/9/12 11:30
 */
object RDDGlom {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array("tian test", "test scala", "scala test", "spark scala"))
        //Return an RDD created by coalescing all elements within each partition into an array.
        val rdd2 = rdd1.glom()
        rdd2.collect.foreach(x => println(x.mkString(",")))
        sc.stop()
    }
}
