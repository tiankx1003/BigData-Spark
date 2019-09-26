package com.tian.output

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.Trigger

/**
 * @author tian
 * @date 2019/9/26 16:27
 * @version 1.0.0
 */
object ConsoleSink {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("ConsoleSink")
            .getOrCreate()
        spark
            .readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .load
            .writeStream
            .outputMode("append")
            .format("console")
            .start()
            .awaitTermination()
        spark.stop()
    }
}
