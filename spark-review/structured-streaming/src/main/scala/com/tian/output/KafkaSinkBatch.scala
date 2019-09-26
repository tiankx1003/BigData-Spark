package com.tian.output

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * Kafka Sink
 * Batch
 *
 * @author tian
 * @date 2019/9/26 16:13
 * @version 1.0.0
 */
object KafkaSinkBatch {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("KafkaSinkBatch")
            .getOrCreate()
        import spark.implicits._
        val arr: Array[String] = Array("hello,hello,tian", "tian,hello")
        val words: DataFrame = spark
            .sparkContext
            .parallelize(arr.flatMap(_.split("\\W+")))
            .toDF("word")
        val wordCount = words
            .groupBy("word")
            .count()
            .map(row => row.getString(0) + "," + row.getLong(1))
        wordCount
            .write
            .format("kafka")
            .option("kafka.bootstrap.servers", "hadoop102:9092,hadoop103:9092,hadoop104:9092")
            .option("topic", "topic1")
            .save()
        spark.stop()
    }
}
