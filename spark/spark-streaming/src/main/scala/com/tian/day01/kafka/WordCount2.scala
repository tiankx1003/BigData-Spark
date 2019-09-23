package com.tian.day01.kafka

import kafka.serializer.StringDecoder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * 实现exactly once语义
 *
 * @author tian
 * @date 2019/9/23 14:05
 * @version 1.0.0
 */
object WordCount2 {
    def main(args: Array[String]): Unit = {
        val ssc: StreamingContext = StreamingContext.getActiveOrCreate("./ck1", createSsc)
        ssc.start()
        ssc.awaitTermination()
    }

    def createSsc(): StreamingContext = {
        println("Flag") //只在第一次时执行
        val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("wordcount")
        val ssc: StreamingContext = new StreamingContext(conf, Seconds(4))
        ssc.checkpoint(".ck1") //一般checkpoint存放在HDFS，但是又会带来小文件问题
        val brokers: String = "hadoop102:9092,hadoop103:9092,hadoop104:9092"
        val topic: String = "first"
        val group: String = "bigdata"
        val kafkaParams: Map[String, String] =
            Map(ConsumerConfig.GROUP_ID_CONFIG -> group, ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> brokers)
        KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
            ssc, kafkaParams, Set(topic)).print() //TODO 没有打印输出会报错??????
        ssc
    }
}
