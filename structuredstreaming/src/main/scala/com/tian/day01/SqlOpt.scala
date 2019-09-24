package com.tian.day01

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types.{LongType, StringType, StructType}

/**
 * 直接执行sql
 *
 * @author tian
 * @date 2019/9/24 18:17
 * @version 1.0.0
 */
object SqlOpt {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("UnTypeOpt")
            .getOrCreate()
        val peopleSchema = new StructType()
            .add("name", StringType)
            .add("age", LongType)
            .add("sex", StringType)
        val peopleDF = spark.readStream
            .schema(peopleSchema)
            .json("file/json") //等价于 .format("json").load(path)
        peopleDF.createOrReplaceTempView("people")
        val df: DataFrame = spark.sql("select * from people")
        df.writeStream
            .outputMode("update")
            .format("console")
            .start
            .awaitTermination()
    }
}
