package com.tian.source

import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * @author tian
 * @date 2019/9/26 11:07
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
