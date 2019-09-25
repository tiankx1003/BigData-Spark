package com.tian.day01.opt

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}

/**
 * 强类型操作API
 *
 * @author tian
 * @date 2019/9/24 18:40
 * @version 1.0.0
 */
object TypeOpt {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("TypeOpt")
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
        import spark.implicits._
        val ds = peopleDF
            .as[People]
            .filter(_.age > 20)
            .map(_.name)
        ds.writeStream
            .outputMode("update")
            .format("console")
            .start
            .awaitTermination()
    }
}

case class People(val name: String, val age: Long, val sex: String)
