package com.tian.day02.window

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.ReceiverInputDStream

/**
 * 基于对源 DStream 窗化的批次进行计算返回一个新的 DStream
 *
 * @author tian
 * @date 2019/9/24 8:58
 * @version 1.0.0
 */
object Window2 {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("")
        val ssc: StreamingContext = new StreamingContext(conf, Seconds(4))
        val sourceDStream: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop102", 9999)
        val sourceDStream2 = sourceDStream.window(Seconds(12), Seconds(8)) //直接返回一个新DStream
        sourceDStream
            .flatMap(_.split(" "))
            .map((_, 1))
            .reduceByKey(_ + _)
            .foreachRDD(rdd => rdd.collect.foreach(print)) //输出算子，直接遍历RDD

        val spark = SparkSession.builder().getOrCreate()
        import spark.implicits._
        sourceDStream
            .flatMap(_.split(" "))
            .map((_, 1))
            .reduceByKey(_ + _)
            .transform(rdd => {
                val df = rdd.toDF("word", "count")
                df.createOrReplaceTempView("w")
                spark.sql("select count from w").rdd
                rdd
            })
            .print

        sourceDStream2
            .flatMap(_.split(" "))
            .map((_, 1))
            .reduceByKey(_ + _)
            .print(100)
        ssc.start()
        ssc.awaitTermination()
    }
}
