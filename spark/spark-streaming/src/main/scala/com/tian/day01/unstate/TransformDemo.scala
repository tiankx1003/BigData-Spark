package com.tian.day01.unstate

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * 无状态转换
 *
 * @author tian
 * @date 2019/9/23 16:15
 * @version 1.0.0
 */
object TransformDemo { //WithStateDemo
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("TransformDemo")
        val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))
        val socketStream: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop102", 9999)
        val resultDStream: DStream[(String, Int)] = socketStream.transform(rdd => {
            rdd.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
        })
        resultDStream.print
        ssc.start()
        ssc.awaitTermination()
    }
}
