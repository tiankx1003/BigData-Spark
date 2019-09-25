package com.tian.day01.opt

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}

/**
 * @author tian
 * @date 2019/9/24 21:25
 * @version 1.0.0
 */
object SqlOpt {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("SqlOpt")
            .getOrCreate()
        val peopleSchema = StructType(
            StructField("name", StringType)
                :: StructField("age", LongType)
                :: StructField("sex", StringType)
                :: Nil
        )
        val peopleDF = spark.readStream
            .schema(peopleSchema)
            .json("file/json")
        peopleDF.createOrReplaceTempView("people")
        val df = spark.sql(
            """
              |select sum(age)
              |from people
              |where age > 15
              |""".stripMargin)
        df.writeStream
            .outputMode("update")
            .format("console")
            .start
            .awaitTermination()
        spark.stop()
    }
}
