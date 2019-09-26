package com.tian.watermark

import java.sql.Timestamp

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * watermark
 * 在 update 模式下, 仅输出与之前批次的结果相比, 涉及更新或新增的数据
 * 在 append 模式中, 仅输出新增的数据, 且输出后的数据无法变更.
 *
 * @author tian
 * @date 2019/9/26 15:11
 * @version 1.0.0
 */
object Watermark {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("Watermark")
            .getOrCreate()
        val lines: DataFrame = spark
            .readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .load
        import spark.implicits._
        val wordTS: DataFrame = lines
            .as[String]
            .flatMap(line => {
                val splits: Array[String] = line.split(",")
                splits(1).split(" ").map((_, Timestamp.valueOf(splits(0))))
            })
            .toDF("word", "timestamp")
        import org.apache.spark.sql.functions._
        val result: DataFrame = wordTS
            .withWatermark("timestamp", "2 minutes")
            .groupBy(
                window($"timestamp", "10 minutes", "2 minutes"),
                $"word"
            )
            .count()
        result
            .writeStream
            //.outputMode("complete") //complete模式下加
            //.outputMode("append") // 输出与之前批次相比有变更的数据(改或增)
            .outputMode("update") //仅输出新增，且输出后的数据不再变更
            .format("console")
            .option("truncate", value = false)
            .start()
            .awaitTermination()
        spark.stop()
    }
}
