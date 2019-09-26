package com.tian.window

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * event-time window
 * 手动传入时间戳
 * 2019-08-26 12:00:00,hello
 *
 * @author tian
 * @date 2019/9/26 14:56
 * @version 1.0.0
 */
object Window2 {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("Window2")
            .getOrCreate()
        val lines: DataFrame = spark
            .readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .load
        import spark.implicits._
        val tsWords: DataFrame = lines
            .as[String]
            .map(line => {
                val splits: Array[String] = line.split(",")
                (splits(0), splits(1))
            })
            .toDF("ts", "word")
        import org.apache.spark.sql.functions._
        val result: DataFrame = tsWords
            .groupBy(
                window($"ts", "10 minutes", "2 minutes"),
                $"word"
            )
            .count()
        result
            .writeStream
            .outputMode("complete")
            .format("console")
            .option("truncate", value = false)
            .start()
            .awaitTermination()
        spark.stop()
    }
}
