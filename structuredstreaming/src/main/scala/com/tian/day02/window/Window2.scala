package com.tian.day02.window

import java.sql.Timestamp

import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * @author tian
 * @date 2019/9/25 19:12
 * @version 1.0.0
 */
object Window2 {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("Window2")
            .getOrCreate()
        import spark.implicits._
        val socketDF: DataFrame = spark
            .readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .option("includeTimestamp", value = true) //自动添加时间戳
            .load
        val df: DataFrame = socketDF
            .as[(String, Timestamp)]
            .flatMap(line => {
                line._1.split(",").map((_, line._2))
            }).toDF("word", "ts")
        import org.apache.spark.sql.functions._
        val result = df
            .groupBy(
                window($"ts", "10 minutes", "3 minutes"),
                $"word"
            )
            .count()
            .orderBy($"window")
        result
            .writeStream
            .outputMode("complete")
            .format("console")
            .trigger(Trigger.ProcessingTime(2000))
            .option("truncate", false)
            .start()
            .awaitTermination()
    }
}
