package com.tian.day01.windowopt

import java.sql.Timestamp

import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * @author tian
 * @date 2019/9/24 18:40
 * @version 1.0.0
 */
object Window1 {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[*]")
            .appName("WordCount1")
            .getOrCreate()

        import spark.implicits._
        val lines: DataFrame = spark.readStream
            .format("socket") // 设置数据源
            .option("host", "localhost")
            .option("port", 10000)
            .option("includeTimestamp", true) // 给产生的数据自动添加时间戳
            .load

        // 把行切割成单词, 保留时间戳
        val words: DataFrame = lines.as[(String, Timestamp)].flatMap(line => {
            line._1.split(" ").map((_, line._2))
        }).toDF("word", "timestamp")

        import org.apache.spark.sql.functions._

        // 按照窗口和单词分组, 并且计算每组的单词的个数
        val wordCounts = words.groupBy(
            // 调用 window 函数, 返回的是一个 Column 参数 1: df 中表示时间戳的列 参数 2: 窗口长度 参数 3: 滑动步长
            window($"s", "10 minutes", "5 minutes"),
            $"word"
        ).count()//.orderBy($"window") // 计数, 并按照窗口排序

        val query: StreamingQuery = wordCounts.writeStream
            .outputMode("complete")
            .format("console")
            .option("truncate", "false") // 不截断.为了在控制台能看到完整信息, 最好设置为 false
            .start
        query.awaitTermination()
    }
}
