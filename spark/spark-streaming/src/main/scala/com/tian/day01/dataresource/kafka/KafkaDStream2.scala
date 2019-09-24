package com.tian.day01.dataresource.kafka

import kafka.serializer.StringDecoder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Kafka数据源
 * Exactly once语义
 * 程序重启后仍旧能够从上次停止前消费的位置开始消费
 *
 * @author tian
 * @date 2019/9/23 20:13
 * @version 1.0.0
 */
object KafkaDStream2 {
    // TODO: 验证
    def main(args: Array[String]): Unit = {
        val ssc = StreamingContext.getActiveOrCreate(".ck1", createSsc)
        ssc.start()
        ssc.awaitTermination()
    }

    def createSsc(): StreamingContext = {
        println("isFlag") //只会再第一次时执行
        val conf = new SparkConf().setMaster("local[2]").setAppName("wordcount")
        val ssc = new StreamingContext(conf, Seconds(4))
        ssc.checkpoint(".ck1")
        val brokers = "hadoop102:9092,hadoop103:9092,hadoop104:9092"
        val topic = "first"
        val group = "bigdata"
        val kafkaParams = Map(ConsumerConfig.GROUP_ID_CONFIG -> group,
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> brokers)
        KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
            ssc, kafkaParams, Set(topic)
        ).print(100)
        ssc
    }
}
