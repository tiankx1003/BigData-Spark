package com.tian.day01.kafka

import kafka.serializer.StringDecoder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Kafka数据源
 *
 * @author tian
 * @date 2019/9/23 11:50
 * @version 1.0.0
 */
//kafka-topics.sh --zookeeper hadoop102:2181 --create --replication-factor 3 --partitions 1 --topic first
//kafka-console-producer.sh --broker-list hadoop102:9092 --topic first
object WordCount1 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setMaster("local[2]").setAppName("wordcount")
        val ssc = new StreamingContext(conf, Seconds(4)) //传入时间间隔
        //kafka参数声明
        val brokers = "hadoop102:9092,hadoop103:9092,hadoop104:9092"
        val topic = "first"
        val group = "bigdata"
        val kafkaParams =
            Map(ConsumerConfig.GROUP_ID_CONFIG -> group, ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> brokers)
        // 使用泛型确定kv类型和kv解码器类型
        KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
            ssc, kafkaParams, Set(topic) //可以订阅多个topic
        ).print() //TODO 当前方法的缺点
        ssc.start()
        ssc.awaitTermination()
    }
}
