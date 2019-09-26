package com.tian.source

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * KafkaSource
 * Batch Queries
 *
 * @author tian
 * @date 2019/9/26 13:53
 * @version 1.0.0
 */
object KafkaSource2 {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("KafkaSource2")
            .getOrCreate()
        val kafkaDF: DataFrame = spark
            .read
            .format("kafka")
            .option("kafka.bootstrap.servers", "hadoop102:9092,hadoop103:9092,hadoop104:9092")
            .option("subscribe", "topic1")
            .option("startingOffsets", """{"topic1":{"0":3}}""") //json格式设置偏移位置
            .option("endingOffsets", "latest")
            .load()
        import spark.implicits._
        val result: DataFrame = kafkaDF
            .selectExpr("cast(value as string)")
            .as[String]
            .flatMap(_.split(" "))
            .groupBy("value")
            .count()
        result
            .write
            .format("console")
            .save
        spark.stop()
    }
}
