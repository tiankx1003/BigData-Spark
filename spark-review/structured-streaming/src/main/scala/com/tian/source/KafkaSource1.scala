package com.tian.source

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * Kafka Source
 * Streaming Queries
 *
 * @author tian
 * @date 2019/9/26 14:06
 * @version 1.0.0
 */
object KafkaSource1 {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("KafkaSource1")
            .getOrCreate()
        val kafkaDF: DataFrame = spark
            .readStream
            .format("kafka")
            .option("kafka.bootstrap.servers",
                "hadoop102:9092,hadoop103:9092,hadoop104:9092")
            .option("subscribe", "topic1")
            .load()
        import spark.implicits._
        val result: DataFrame = kafkaDF
            .selectExpr("cast (value as string)")
            .as[String]
            .flatMap(_.split(" "))
            .groupBy("value")
            .count()
        result
            .writeStream
            .format("console")
            .outputMode("complete")
            .start()
            .awaitTermination()
        spark.stop()
    }
}
