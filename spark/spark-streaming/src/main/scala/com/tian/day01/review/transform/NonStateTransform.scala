package com.tian.day01.review.transform

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * 无状态转换
 *
 * @author tian
 * @date 2019/9/23 20:12
 * @version 1.0.0
 */
object NonStateTransform {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setMaster("local[2]").setAppName("NonStateTransform")
        val ssc = new StreamingContext(conf, Seconds(3))
        val socketSteam = ssc.socketTextStream("hadoop02", 9999)
        val resultDStream =
            socketSteam.transform(_.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _))
        resultDStream.print
        ssc.start()
        ssc.awaitTermination()
    }
}
