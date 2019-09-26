package com.tian.opt

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}

/**
 * 强类型API操作
 *
 * @author tian
 * @date 2019/9/26 14:22
 * @version 1.0.0
 */
object TypeOption {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("TypeOption")
            .getOrCreate()
        val peopleSchema: StructType = StructType(
            StructField("name", StringType)
                :: StructField("age", LongType)
                :: StructField("sex", StringType)
                :: Nil
        )
        val peopleDF = spark
            .readStream
            .schema(peopleSchema)
            .json("file/json")
        import spark.implicits._
        val resultDS = peopleDF
            .as[People]
            .filter(_.age > 20)
            .map(_.name)
        resultDS
            .writeStream
            .outputMode("update")
            .format("console")
            .start()
            .awaitTermination()
        spark.stop()
    }
}

case class People(val name: String, val age: Long, val sex: String)
