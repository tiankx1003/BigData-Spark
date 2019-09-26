package com.tian.output

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * @author tian
 * @date 2019/9/26 15:53
 * @version 1.0.0
 */
object FileSink {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("FileSink")
            .getOrCreate()
        val socketDF: DataFrame = spark
            .readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .load
        import spark.implicits._
        val words: DataFrame = socketDF
            .as[String]
            .flatMap(line => {
                line.split("\\W+").map(word => {
                    (word, word.reverse)
                })
            })
            .toDF("word", "reword")
        words
            .writeStream
            .outputMode("append")
            .format("json") //支持orc json csv
            .option("path", "file/fileSink") //输出目录
            .option("checkpointLocation", "file/ck1") //必须指定checkpoint目录
            .start()
            .awaitTermination()
        spark.stop()
    }
}
