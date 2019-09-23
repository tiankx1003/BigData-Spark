package com.tian.day01.review

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Socket数据源
 * 程序运行之前需要服务器端启动了netcat，否则会报连接异常
 * 这种方式只能统计每四秒内的wordcount，且每个间隔没有关联
 *
 * @author tian
 * @date 2019/9/23 20:07
 * @version 1.0.0
 */
object WordCount {
    def main(args: Array[String]): Unit = {
        // 创建StreamingContext
        val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wordcount")
        val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))
        // 核心数据集
        val socketSteam: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop102", 9999)
        val wordCountDStream: DStream[(String, Int)] = socketSteam //operate DStream
            .flatMap(_.split(" "))
            .map((_, 1))
            .reduceByKey(_ + _)
        wordCountDStream.print(100) //print data in 4s
        // 开启StreamingContext
        ssc.start()
        // 防止当前线程退出
        ssc.awaitTermination()
    }
}
