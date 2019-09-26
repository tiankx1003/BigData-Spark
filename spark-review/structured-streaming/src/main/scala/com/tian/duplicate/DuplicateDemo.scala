package com.tian.duplicate

import java.sql.Timestamp

import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * @author tian
 * @date 2019/9/26 15:26
 * @version 1.0.0
 */
object DuplicateDemo {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("DuplicateDemo")
            .getOrCreate()
        val lines: DataFrame = spark
            .readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .load
        import spark.implicits._
        val words: DataFrame = lines
            .as[String]
            .map(line => {
                val arr: Array[String] = line.split(",")
                (arr(0), Timestamp.valueOf(arr(1)), arr(2))
            })
            .toDF("uid", "ts", "word")
        val wordcount = words
            .withWatermark("ts", "2 minutes")
            .dropDuplicates("uid") //按照uid去重
        wordcount
            .writeStream
            .outputMode("append")
            .format("console")
            .trigger(Trigger.ProcessingTime(0))
            .start()
            .awaitTermination()
        spark.stop()
    }
}
