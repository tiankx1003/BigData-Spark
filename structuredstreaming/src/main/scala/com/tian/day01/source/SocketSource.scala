package com.tian.day01.source

import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * @author tian
 * @date 2019/9/24 18:37
 * @version 1.0.0
 */
object SocketSource {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("SocketSource")
            .getOrCreate()
        val lines: DataFrame = spark //输入表
            .readStream
            .format("socket") //source类型
            .option("host", "hadoop102")
            .option("port", "9999")
            .load
        import spark.implicits._
        val df: DataFrame = lines
            .as[String]
            .flatMap(_.split("\\W+"))
            .map((_, 1))
            .groupBy("value")
            .count
        lines
            .as[String]
            .flatMap(_.split((" ")))
            .createOrReplaceTempView("w")
        //sql的写法
        val wordcount: DataFrame = spark.sql(
            """
              |select value word, count(1) count
              |from w
              |group by value
              |""".stripMargin)
        //wordcount
        df
            .writeStream //输出表
            .format("console") //输出目的地
            .outputMode("complete") //输出模式
            .trigger(Trigger.ProcessingTime("2 seconds"))
            .start
            .awaitTermination()
        spark.stop()
    }
}

/*
socket source在生产环境中很少使用，主要用于测试
输出模式:
complete
    全部输出，必须有聚合
append
    追加模式，只有输出那些将来永远不可能再更新的数据
    没有聚合的时候，append和update一致
    有聚合的时候，一定要有水印才能使用append
update
    只输出变化的部分
 */
