package com.tian.day01

import org.apache.spark.sql.SparkSession

/**
 * Kafka Source
 * Streaming Queries
 *
 * @author tian
 * @date 2019/9/24 15:12
 * @version 1.0.0
 */
object KafkaSource1 {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("KafkaSource")
            .getOrCreate()
        import spark.implicits._
        spark
            .readStream
            .format("kafka")
            .option("kafka.bootstrap.servers", "hadoop102:9092,hadoop103:9092,hadoop104:9092")
            .option("subscribe", "topic1")
            .load
            //.select("value","key")
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
