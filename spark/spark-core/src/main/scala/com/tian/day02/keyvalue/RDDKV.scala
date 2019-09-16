package com.tian.day02.keyvalue

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/12 16:16
 * @version 1.0.0
 */
object RDDKV {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array((10, 1), (20, 2), (30, 3), (40, 4)))
        //非键值对类型的rdd一定没有partitioner，键值对类型的rdd不一定有partitioner
        println(rdd1.partitioner) //null
        println(sc.parallelize(List(10, 20, 30, 40)).groupBy(x => x).partitioner) //HashPartitioner
        //隐式转换
        //implicit def rddToPairRDDFunctions
        //之前没有分区器才会使用传入的分区器，分区器传入分区数
        val rdd2 = rdd1.partitionBy(new HashPartitioner(3))
        rdd2.glom().collect.foreach(arr => println(arr.mkString(",")))
        //TODO 分区器 视频
    }
}
