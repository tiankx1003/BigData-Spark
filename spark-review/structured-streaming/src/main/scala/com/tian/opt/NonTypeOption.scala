package com.tian.opt

import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * 弱类型API操作
 *
 * @author tian
 * @date 2019/9/26 14:22
 * @version 1.0.0
 */
object NonTypeOption {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("NonTypeOption")
            .getOrCreate()
        val peopleSchema: StructType = StructType(
            StructField("name", StringType)
                :: StructField("age", LongType)
                :: StructField("sex", StringType)
                :: Nil
        )
        val peopleDF: DataFrame = spark
            .readStream
            .schema(peopleSchema)
            .json("file/json")
        val result: DataFrame = peopleDF
            .select("name", "age", "sex")
            .where("age>20")
            .groupBy("sex")
            .sum("age")
        result
            .writeStream
            .outputMode("update")
            .format("console")
            .trigger(Trigger.ProcessingTime(0))
            .start()
            .awaitTermination()
        spark.stop()
    }
}
