package com.tian.day01

import org.apache.spark.sql.types.{LongType, StringType, StructType}
import org.apache.spark.sql.{Dataset, SparkSession}

/**
 * 强类型API
 *
 * @author tian
 * @date 2019/9/24 18:18
 * @version 1.0.0
 */
object TypeOpt {
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
        import spark.implicits._
        val ds: Dataset[String] = peopleDF.as[People].filter(_.age > 20).map(_.name) //强类型API
        ds.writeStream
            .outputMode("update")
            .format("console")
            .start
            .awaitTermination()
    }
}

case class People(name: String, age: Long, sex: String)
