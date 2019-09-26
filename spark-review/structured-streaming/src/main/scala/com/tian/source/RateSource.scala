package com.tian.source

import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * Rate Source
 * 以固定的速率生成固定格式的数据，用来测试Structured Streaming的性能
 *
 * @author tian
 * @date 2019/9/26 14:18
 * @version 1.0.0
 */
object RateSource {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("RateSource")
            .getOrCreate()
        val rateDF: DataFrame = spark
            .readStream
            .format("rate")
            .option("rowPerSecond", 10)
            .option("rampUpTime", 1)
            .option("numPartitions", 3)
            .load()
        rateDF
            .writeStream
            .outputMode("update")
            .format("console")
            .option("truncate", value = false)
            .start()
            .awaitTermination()
        spark.stop()
    }
}
