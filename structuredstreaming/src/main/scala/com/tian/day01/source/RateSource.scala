package com.tian.day01.source

import org.apache.spark.sql.SparkSession

/**
 * Rate Source
 *
 * @author tian
 * @date 2019/9/24 16:19
 * @version 1.0.0
 */
object RateSource {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("RateSource")
            .getOrCreate()
        val df = spark.readStream.format("rate")
            .option("rowsPerSecond", 10)
            .option("rampUpTime", 1)
            .option("numPartitions", 3)
            .load()
        df.writeStream
            .format("console")
            .outputMode("update")
            .option("truncate", value = false) //关闭省略显示
            .start()
            .awaitTermination()
    }
}
