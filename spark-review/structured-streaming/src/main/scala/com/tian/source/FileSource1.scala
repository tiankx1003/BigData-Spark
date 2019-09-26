package com.tian.source

import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
 * @author tian
 * @date 2019/9/26 11:15
 * @version 1.0.0
 */
object FileSource1 {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("FileSource")
            .getOrCreate()
        val userSchema: StructType = StructType(
            StructField("name", StringType)
                :: StructField("age", IntegerType)
                :: StructField("sex", StringType)
                :: Nil
        )
        val fileDF: DataFrame = spark
            .readStream
            .schema(userSchema)
            .csv("file/file1")
        fileDF
            .writeStream
            .outputMode("update")
            .format("console")
            .trigger(Trigger.ProcessingTime(0))
            .start()
            .awaitTermination()
        spark.stop()
    }
}
