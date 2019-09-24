package com.tian.structuredstreaming.day01

import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * 案例
 * socket source数据源
 *
 * @author tian
 * @date 2019/9/24 10:40
 * @version 1.0.0
 */
object WordCount {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[*]")
            .appName("WordCount1")
            .getOrCreate()
        import spark.implicits._
        val lines: DataFrame = spark.readStream //输入表
            .format("socket") //socket source，生产环境中很少使用，主要用于测试
            .option("host", "hadoop102")
            .option("port", 9999)
            .load
        val df: DataFrame = lines.as[String]
            .flatMap(_.split(" "))
            .map((_, 1))
            .groupBy("value")
            .count()
        val result: StreamingQuery = df.writeStream //输出表
            .format("console") //输出目的地
            .outputMode("update") //输出模式
            .start
        result.awaitTermination()
        spark.stop()
    }
}
