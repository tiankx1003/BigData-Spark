package com.tian.opt

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types.{LongType, StringType, StructType}

/**
 * 直接执行sql
 *
 * @author tian
 * @date 2019/9/26 14:37
 * @version 1.0.0
 */
object SqlOption {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("SqlOption")
            .getOrCreate()
        val peopleSchema: StructType = new StructType()
            .add("name", StringType)
            .add("age", LongType)
            .add("sex", StringType)
        val peopleDF: DataFrame = spark
            .readStream
            .schema(peopleSchema)
            .json("file/json")
        peopleDF.createOrReplaceTempView("people")
        val resultDF: DataFrame = spark.sql(
            """
              |select sum(age)
              |from people
              |where age > 15
              |""".stripMargin)
        resultDF
            .writeStream
            .outputMode("update")
            .format("console")
            .start()
            .awaitTermination()
        spark.stop()
    }
}
