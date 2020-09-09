package com.tian.day01.dataresource.kafka

import kafka.serializer.StringDecoder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Kafka数据源
 * 这种方法存在缺陷
 * 如果该程序停掉后，kafka生产者仍在消费数据
 * 程序重启后只能从当前时间节点对应的位置开始消费
 * 无法完成从断点位置消费的需要
 *
 * @author tian
 * @date 2019/9/23 20:11
 * @version 1.0.0
 */
/*
创建topic命令
kafka-topics.sh --zookeeper hadoop102:2181 --create --replication-factor 3 --partitions 1 --topic first
生产信息命令
kafka-console-producer.sh --broker-list hadoop102:9092 --topic first
 */
object KafkaDStream1 {
    // TODO: 运行验证
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Kafka1")
        val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))
        //kafka参数声明
        val brokers: String = "hadoop102:9092,hadoop103:9092,hadoop104:9092"
        val topic: String = "first"
        val group: String = "bigdata"
        val kafkaParams: Map[String, String] =
            Map(ConsumerConfig.GROUP_ID_CONFIG -> group, ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> brokers)
        //使用泛型确定kv类型和kv解码器类型
        KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
            ssc,
            kafkaParams,
            Set(topic) //支持订阅多个topic
        ).print()
        ssc.start()
        ssc.awaitTermination()
    }
}
