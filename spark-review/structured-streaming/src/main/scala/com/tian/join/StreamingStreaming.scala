package com.tian.join

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.streaming.Trigger

/**
 * Steaming join Streaming
 *
 * @author tian
 * @date 2019/9/26 15:45
 * @version 1.0.0
 */
object StreamingStreaming {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("StreamingStreaming")
            .getOrCreate()
        import spark.implicits._
        val ageDF: DataFrame = spark
            .readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .load
            .as[String]
            .map(line => {
                val splits: Array[String] = line.split(",")
                (splits(0), splits(1))
            })
            .toDF("name", "age")
        val sexDF: DataFrame = spark
            .readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 8888)
            .load
            .as[String]
            .map(line => {
                val splits: Array[String] = line.split(",")
                (splits(0), splits(1))
            })
            .toDF("name", "sex")
        val joinedDF: DataFrame = ageDF.join(sexDF, "name")
        joinedDF
            .writeStream
            .outputMode("append")
            .format("console")
            .trigger(Trigger.ProcessingTime(0))
            .start()
            .awaitTermination()
        spark.stop()
    }
}
