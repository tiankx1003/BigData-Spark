package com.tian.day01.source

import org.apache.spark.sql.SparkSession

/**
 * Kafka Source
 * Batch Queries
 * 批处理的方式不是流的方式，执行一次就直接结束了
 *
 * @author tian
 * @date 2019/9/24 15:12
 * @version 1.0.0
 */
object KafkaSource2 {
    def main(args: Array[String]): Unit = {
        val spark = SparkSession
            .builder()
            .master("local[2]")
            .appName("KafkaSource")
            .getOrCreate()
        import spark.implicits._
        spark
            .read //批处理的处理方式，不是流的处理方式
            .format("kafka")
            .option("kafka.bootstrap.servers", "hadoop102:9092,hadoop103:9092,hadoop104:9092")
            .option("subscribe", "topic1")
            //.option("startingOffsets", "earliest") //默认开始的offset为最早
            //Expected e.g. {"topicA":{"0":23,"1":-1},"topicB":{"0":-2}}
            .option("startingOffsets", """{"topic1":{"0":3}}""") //设置开始位置偏移量
            .option("endingOffsets","latest") //结束的offset位置
            .load
            //.select("value","key")
            .selectExpr("cast(value as string)")
            .as[String]
            .flatMap(_.split(" "))
            .groupBy("value")
            .count()
            .write //批处理的方式写，不是流的写方式
            .format("console")
            .save()
        //不是流则没有以下操作
        //.outputMode("update")
        //.start
        //.awaitTermination()
    }
}
