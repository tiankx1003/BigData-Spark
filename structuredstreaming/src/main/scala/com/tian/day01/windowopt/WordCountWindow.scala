package com.tian.day01.windowopt

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.execution.streaming.FileStreamSource.Timestamp

/**
 * @author tian
 * @date 2019/9/24 18:40
 * @version 1.0.0
 */
object WordCountWindow {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("WordCountWindow")
            .getOrCreate()
        import spark.implicits._
        import org.apache.spark.sql.functions._
        val line = spark.readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .option("includeTimeStamp", value = true)
            .load
            .as[(String, Timestamp)]
            .flatMap {
                case (word, ts) => word.split("\\W+").map((_, ts))
            }
            .toDF("word", "ts")
            .groupBy(
                window($"ts", "4 minutes", "2 minutes"), $"word"
            )
            .count
            .orderBy($"window")
        line.writeStream
            .format("console")
            .outputMode("update")
            .start()
            .awaitTermination()
    }
}
