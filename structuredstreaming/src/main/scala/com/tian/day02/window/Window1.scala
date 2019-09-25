package com.tian.day02.window

import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
 * 事件时间窗口操作
 * 手动传入时间戳和字符串
 *
 * @author tian
 * @date 2019/9/25 10:19
 * @version 1.0.0
 */
/*
2019-09-25 19:03:10,good
2019-09-25 19:04:10,good
2019-09-25 19:04:27,good
2019-09-25 19:15:27,good
2019-09-25 19:19:27,good
 */
object Window1 { // TODO: 窗口生成规则
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("Window1")
            .getOrCreate()
        val socketDF = spark
            .readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .load
        import spark.implicits._
        import org.apache.spark.sql.functions._
        val df: DataFrame = socketDF
            .as[String]
            .map(line => {
                val split: Array[String] = line.split(",")
                (split(0), split(1))
            })
            .toDF("ts", "word") //手动传入时间戳和word，2019-09-25 19:03:27,good
        val result: Dataset[Row] = df.groupBy( //根据窗口和word分组
            window($"ts", "10 minutes", "3 minutes"), //设计窗口
            $"word"
        )
            .count()
            //.groupBy($"window") //同上
            .sort("window") //只有在输出模式为complete时才可以使用排序
        result
            .writeStream
            .outputMode("complete") //只在有聚合时才可以是哟个complete
            .format("console")
            .trigger(Trigger.ProcessingTime(2000)) //两秒触发一次
            .start()
            .awaitTermination()
    }
}
