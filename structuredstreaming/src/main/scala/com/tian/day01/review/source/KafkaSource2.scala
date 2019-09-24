package com.tian.day01.review.source

import org.apache.spark.sql.SparkSession

/**
 * Kafka Source
 * Batch Queries
 * 批处理查询
 *
 * @author tian
 * @date 2019/9/24 18:39
 * @version 1.0.0
 */
object KafkaSource2 {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("KafkaSource2")
            .getOrCreate()
        import spark.implicits._
        spark
            .read
            .format("kafka")
            .option("kafka.bootstrap.servers", "hadoop102:9092,hadoop103:9092,hadoop104:9092")
            .option("subscribe", "topic1")
            //.option("startOffsets","earliest") //默认的开始offset位置为最早
            .option("startingOffsets", """{"topic1":{"0":3}}""") //json格式设置偏移位置
            .option("endingOffsets", "latest") //结束的offset位置
            .load
            //.select("value","key") //TODO 有问题???
            .selectExpr("cast(value as string)")
            .as[String]
            .flatMap(_.split(" "))
            .groupBy("value")
            .count()
            .write
            .format("console")
            .save
        spark.stop()

    }
}
