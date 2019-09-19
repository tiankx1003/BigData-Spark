package com.tian.day04.readwrite

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
 * @author tian
 * @date 2019/9/18 18:44
 * @version 1.0.0
 */
object TestPath {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[*]")
        val sc = new SparkContext(conf)
        val line1: RDD[String] = sc.textFile("file://" + ClassLoader.getSystemResource("agent.log"))
        val line2: RDD[String] = sc.textFile(ClassLoader.getSystemResource("agent.log").getPath)
        // TODO: 路径报错
        //        line1.foreach(println)
        line2.foreach(println)
        println(ClassLoader.getSystemResource("agent.log").getPath)
        sc.stop()
    }
}

/**
 * Read a text file from HDFS, a local file system (available on all nodes), or any
 * Hadoop-supported file system URI, and return it as an RDD of Strings.
 */
