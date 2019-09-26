package com.tian.output

import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * Kafka Sink
 * Steaming
 *
 * @author tian
 * @date 2019/9/26 16:01
 * @version 1.0.0
 */
/*
kafka消费数据
kafka-console-consumer.sh --bootstrap-server hadoop102:9092 --topic topic1
 */
object KafkaSinkStreaming {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("KafkaSink")
            .getOrCreate()
        val socketDF: DataFrame = spark
            .readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .load
        import spark.implicits._
        val words: DataFrame = socketDF
            .as[String]
            .flatMap(_.split("\\W+"))
            .groupBy("value")
            .count()
            .map(row => row.getString(0) + "," + row.getLong(1))
            .toDF("value")
        words
            .writeStream
            .outputMode("update")
            .format("kafka")
            .trigger(Trigger.ProcessingTime(0))
            .option("kafka.bootstrap.servers", "hadoop102:9092,hadoop103:9092,hadoop104:9092")
            .option("topic", "topic1")
            .option("checkpointLocation", "file/ck2") //流式必须指定checkpoint位置
            .start()
            .awaitTermination()
        spark.stop()
    }
}
