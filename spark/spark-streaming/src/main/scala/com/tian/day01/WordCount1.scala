package com.tian.day01

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * socket数据源WordCount
 *
 * @author tian
 * @date 2019/9/23 9:03
 * @version 1.0.0
 */
object WordCount1 {
    def main(args: Array[String]): Unit = {
        //1. 创建StreamingContext
        val conf = new SparkConf().setMaster("local[2]").setAppName("wordcount")
        val ssc = new StreamingContext(conf, Seconds(4)) //传入时间间隔
        //2. 核心数据集: DStream
        val socketStream = ssc.socketTextStream("hadoop102", 9999)
        //3. 对DStreaming各种操作
        val wordCountDStream = socketStream
            .flatMap(_.split(" "))
            .map((_, 1))
            .reduceByKey(_ + _)
        //4. 最终数据的处理: 打印
        wordCountDStream.print(100) // 只打印所在4秒内的数据，TODO: 有问题!!!!
        //5. 启动StreamingContext
        ssc.start() //nc -lk 9999
        //6. 阻止当前线程退出
        ssc.awaitTermination() //等待ssc结束，主线程才结束
    }
}
