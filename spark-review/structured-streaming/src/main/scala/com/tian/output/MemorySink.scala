package com.tian.output

import java.util.{Timer, TimerTask}

import org.apache
import org.apache.spark
import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * @author tian
 * @date 2019/9/26 16:28
 * @version 1.0.0
 */
object MemorySink {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("MemorySink")
            .getOrCreate()
        val lines: DataFrame = spark
            .readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .load
        import spark.implicits._
        val words: DataFrame = lines
            .as[String]
            .flatMap(_.split("\\W+"))
            .groupBy("value")
            .count()
        val query: StreamingQuery = words
            .writeStream
            .outputMode("complete")
            .format("memory")
            .queryName("word_count") //内存临时表
            .start()
        //使用定时器测试内存临时表
        val timer: Timer = new Timer(true)
        val task: TimerTask = new TimerTask {
            override def run(): Unit = spark.sql("select * from word_count").show()
        }
        timer.scheduleAtFixedRate(task, 0, 2000)
        query.awaitTermination()
        spark.stop()
    }
}
