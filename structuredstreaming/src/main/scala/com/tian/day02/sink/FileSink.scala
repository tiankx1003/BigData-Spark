package com.tian.day02.sink

import org.apache.spark.sql.SparkSession

/**
 * @author tian
 * @date 2019/9/25 20:34
 * @version 1.0.0
 */
object FileSink {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("FileSink")
            .getOrCreate()
        spark
            .readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .load
    }
}
