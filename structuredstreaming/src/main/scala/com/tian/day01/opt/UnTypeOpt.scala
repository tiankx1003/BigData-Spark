package com.tian.day01.opt

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{LongType, StringType, StructType}

/**
 * 弱类型操作API
 *
 * @author tian
 * @date 2019/9/24 18:40
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
            .json("file/json")
        val df = peopleDF.select("name", "age", "sex")
            .where("age>20").groupBy("sex").sum("age")
        df
            .writeStream
            .outputMode("update")
            .format("console")
            .start
            .awaitTermination()
    }
}
