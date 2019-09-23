package com.tian.day01

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

/**
 * rdd数据源WordCount
 *
 * @author tian
 * @date 2019/9/23 10:00
 * @version 1.0.0
 */
object WordCount2 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setMaster("local[2]").setAppName("wordcount2")
        val ssc = new StreamingContext(conf, Seconds(3))
        val rddQueue = mutable.Queue[RDD[Int]]() //数据源为rdd队列
        val resultDStream = ssc.queueStream(rddQueue, false).reduce(_ + _)
        resultDStream.print
        ssc.start()
        while (true) {
            rddQueue.enqueue(ssc.sparkContext.parallelize(1 to 100))
            Thread.sleep(1000) //每秒处理一个
        }
        ssc.awaitTermination()
    }
}
