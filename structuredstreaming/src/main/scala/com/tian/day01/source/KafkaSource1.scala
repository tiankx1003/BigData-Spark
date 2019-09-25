package com.tian.day01.source

import org.apache.spark.sql.SparkSession

/**
 * Kafka Source
 * Streaming Queries
 * 流式处理查询
 *
 * @author tian
 * @date 2019/9/24 18:38
 * @version 1.0.0
 */
/*
查看topic
kafka-topics.sh --zookeeper hadoop102:2181 --list
创建topic
kafka-topics.sh --zookeeper hadoop102:2181 --create --replication-factor 3 --partitions 1 --topic first
生产消息
kafka-console-producer.sh --broker-list hadoop102:9092 --topic topic1
 */
object KafkaSource1 {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("KafkaSource1")
            .getOrCreate()
        import spark.implicits._
        spark.readStream
            .format("kafka")
            .option("kafka.bootstrap.servers", "hadoop102:9092,hadoop103:9092,hadoop104:9092")
            .option("subscribe", "topic1")
            .load()
            .selectExpr("cast(value as string)")
            .as[String]
            .flatMap(_.split(" "))
            .groupBy("value")
            .count()
            .writeStream
            .format("console")
            .outputMode("update")
            .start
            .awaitTermination()
    }
}
