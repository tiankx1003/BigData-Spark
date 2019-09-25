package com.tian.day02.watermark

import java.sql.Timestamp

import org.apache.spark.sql.streaming.{StreamingQuery, Trigger}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
 * 水印用于清除内存中的过期数据
 * 初始化水印为0
 *
 * @author tian
 * @date 2019/9/25 10:19
 * @version 1.0.0
 */
/*
2019-09-25 10:20:00,hello hello hello
 */
object Watermark {
    def main(args: Array[String]): Unit = {

        val spark: SparkSession = SparkSession
            .builder()
            .master("local[*]")
            .appName("WordCountWatermark1")
            .getOrCreate()

        import spark.implicits._
        val lines: DataFrame = spark.readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .load

        // 输入的数据中包含时间戳, 而不是自动添加的时间戳
        val words: DataFrame = lines.as[String].flatMap(line => {
            val split: Array[String] = line.split(", ")
            split(1).split(" ").map((_, Timestamp.valueOf(split(0))))
        }).toDF("word", "timestamp")

        import org.apache.spark.sql.functions._


        val wordCounts: Dataset[Row] = words
            // 添加watermark, 参数 1: event-time 所在列的列名 参数 2: 延迟时间的上限.
            .withWatermark("timestamp", "2 minutes")
            .groupBy(window($"timestamp", "10 minutes", "2 minutes"), $"word")
            .count()

        val query: StreamingQuery = wordCounts.writeStream
            .outputMode("update") // complete模式下加水印没有意义
            .trigger(Trigger.ProcessingTime(1000))
            .format("console")
            .option("truncate", "false")
            .start
        query.awaitTermination()
    }
}
