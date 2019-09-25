package com.tian.day02.duplicate

import java.sql.Timestamp

import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
 * 去重
 *
 * @author tian
 * @date 2019/9/25 14:01
 * @version 1.0.0
 */
/*
tian,2019-08-25 18:35:00,tian1
tian,2019-08-25 18:35:10,tian2
good,2019-08-25 18:38:10,tian1
mark,2019-08-25 18:30:00,tian1
超过水印阈值(超时)不会输出
重复时不会输出
 */
object DropDuplicate {
    def main(args: Array[String]): Unit = {
        //创建SparkSession
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("Duplicate")
            .getOrCreate()
        import spark.implicits._
        //确定数据源并加载数据
        val lines: DataFrame = spark
            .readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .load
        //对加载的数据转成DS并处理(切割并返回信息)，
        val words: DataFrame = lines.as[String].map(lines => {
            val arr: Array[String] = lines.split(",")
            (arr(0), Timestamp.valueOf(arr(1)), arr(2))
        }).toDF("uid", "ts", "word") //转成DF
        val wordCounts: Dataset[Row] = words
            .withWatermark("ts", "2 minutes")
            .dropDuplicates("uid") //去除重复数据，可以传递多个字段
        wordCounts
            .writeStream
            .outputMode("append")
            .format("console")
            .trigger(Trigger.ProcessingTime(0))
            .start()
            .awaitTermination()
    }
}
