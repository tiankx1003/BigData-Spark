package com.tian.day02.joint

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.streaming.Trigger


/**
 * 流式数据和流式数据连接
 *
 * @author tian
 * @date 2019/9/25 14:19
 * @version 1.0.0
 */
object StreamStreamJoin {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("StreamStreamJoin")
            .getOrCreate()
        import spark.implicits._
        val ageDF = spark.readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 8888)
            .load
            .as[String]
            .map(line => {
                val splits = line.split(",")
                (splits(0), splits(1))
            })
            .toDF("name", "age")
        val sexDF = spark.readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .load
            .as[String]
            .map(line => {
                val splits = line.split(",")
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
    }
}
