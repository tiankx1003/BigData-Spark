package com.tian.source

import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
 * 读取目录结构文件
 *
 * @author tian
 * @date 2019/9/26 11:48
 * @version 1.0.0
 */
object FileSource2 {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("FileSource2")
            .getOrCreate()
        val userSchema: StructType = StructType(
            StructField("name", StringType)
                :: StructField("age", IntegerType)
                :: StructField("sex", StringType)
                :: Nil
        )
        val fileDF: DataFrame = spark
            .readStream
            .format("csv")
            .schema(userSchema)
            .load("file/file2")
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
