package com.tian.day01

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
 * @author tian
 * @date 2019/9/24 14:41
 * @version 1.0.0
 */
object FileSource {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("FileSource")
            .getOrCreate()
        val userSchema: StructType =
            StructType(
                StructField("name", StringType)
                    :: StructField("age", IntegerType)
                    :: StructField("sex", StringType)
                    :: Nil
            )
        val df1: DataFrame = spark.readStream
            .format("csv")
            .schema(userSchema)
            .load("file1") //必须是目录，不能时文件名
        val df2: DataFrame = spark.readStream //读取自动分区的文件夹内的文件
            .schema(userSchema)
            .csv("file2") //另一种获取DF的方式
            .groupBy("sex").sum("age") //聚合操作
        //df1
        df2
            .writeStream
            .format("console")
            .outputMode("update") //没有聚合不能使用complete
            .trigger(Trigger.ProcessingTime(0)) //触发器时间内传入零表示立即处理
            .start()
            .awaitTermination()
    }
}
