package com.tian.day01

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.execution.streaming.FileStreamSource.Timestamp

/**
 * @author tian
 * @date 2019/9/24 16:56
 * @version 1.0.0
 */
object Window1 {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("Window1")
            .getOrCreate()
        import spark.implicits._
        //导入spark提供的全局的函数
        import org.apache.spark.sql.functions._
        val line: DataFrame = spark.readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .option("includeTimestamp", true)
            .load
            .as[(String, Timestamp)]
            .flatMap {
                case (words, ts) => words.split("\\W+").map((_, ts))
            }
            .toDF("word", "ts")
            .groupBy(
                window($"ts", "4 minutes", "2 minutes"), $"word"
            )
            .count()
        line.writeStream
            .format("console")
            .outputMode("update")
            .start()
            .awaitTermination()
        // TODO: 视频
    }
}