package com.tian.day01.review.source

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
 * @author tian
 * @date 2019/9/24 18:37
 * @version 1.0.0
 */
object FileSource {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("FileSource")
            .getOrCreate()
        val userSchema = StructType(
            StructField("name", StringType)
                :: StructField("age", IntegerType)
                :: StructField("sex", StringType)
                :: Nil
        )
        val df1: DataFrame = spark
            .readStream
            .schema(userSchema)
            .csv("file/file1") //只能是目录名
        val df2: DataFrame = spark
            .readStream
            .format("csv")
            .schema(userSchema)
            .load("file/file2")
        df2
            //        df1
            .writeStream
            .format("console")
            .outputMode("update") //没有聚合不能使用complete
            .trigger(Trigger.ProcessingTime(0)) //立即处理
            .start
            .awaitTermination
        spark.stop()
    }
}
