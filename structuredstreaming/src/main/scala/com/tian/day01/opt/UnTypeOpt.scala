package com.tian.day01.opt

import org.apache.spark.sql.types.{LongType, StringType, StructType}
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * 弱类型API
 *
 * @author tian
 * @date 2019/9/24 16:28
 * @version 1.0.0
 */
object UnTypeOpt {
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
        val df: DataFrame = peopleDF.select("name", "age", "sex")
            .where("age>20").groupBy("sex").sum("age")
        df
            .writeStream
            .outputMode("update")
            .format("console")
            .start
            .awaitTermination()
    }
}
