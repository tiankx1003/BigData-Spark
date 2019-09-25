package com.tian.day01.source

import org.apache.spark.sql.SparkSession

/**
 * Rate Source
 *
 * @author tian
 * @date 2019/9/24 18:38
 * @version 1.0.0
 */
/*
+-----------------------+-----+
|timestamp              |value|
+-----------------------+-----+
|2019-09-24 20:51:31.971|5    |
+-----------------------+-----+
 */
object RateSource { // TODO: ???
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("RateSource")
            .getOrCreate()
        val df = spark.readStream.format("rate")
            .option("rowPerSecond", 10)
            .option("rampUpTime", 1)
            .option("numPartitions", 3)
            .load()
        df.writeStream
            .format("console")
            .outputMode("update")
            .option("truncate", value = false) //默认为true，显示不全，使用省略号代替
            .start()
            .awaitTermination()
    }
}
