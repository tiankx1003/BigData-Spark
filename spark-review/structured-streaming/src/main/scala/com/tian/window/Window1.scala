package com.tian.window

import java.sql.Timestamp
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
 * event-time window
 * 自动生成时间戳
 *
 * @author tian
 * @date 2019/9/26 14:42
 * @version 1.0.0
 */
object Window1 {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("Window1")
            .getOrCreate()
        val lines: DataFrame = spark
            .readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .option("includeTimestamp", value = true) //自动添加时间戳
            .load
        import spark.implicits._
        val words: DataFrame = lines
            .as[(String, Timestamp)]
            .flatMap(line => {
                line._1.split(" ").map((_, line._2))
            })
            .toDF("word", "timestamp")
        import org.apache.spark.sql.functions._
        val wordcount: Dataset[Row] = words
            .groupBy( //设计窗口并按窗口和word分组
                window($"timestamp", "10 minutes", "5 minutes"),
                $"word"
            )
            .count()
            //只有在complete输出模式时才能使用排序
            //.orderBy($"window") //按照窗口排序
            .sort("window") //按照窗口排序
        wordcount
            .writeStream
            .outputMode("complete") //只有聚合时才能使用complete
            .format("console")
            .option("truncate", value = false) //不截断显示
            .start()
            .awaitTermination()
        spark.stop()
    }
}

// TODO: 窗口生成规则
