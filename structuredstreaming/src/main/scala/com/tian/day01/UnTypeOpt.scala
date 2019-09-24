package com.tian.day01

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.sql.types.{LongType, StringType, StructType}

/**
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
            .where("age>20").groupBy("sex").sum("age") //弱类型API
        import spark.implicits._
        val ds: Dataset[String] = peopleDF.as[People].filter(_.age > 20).map(_.name) //强类型API
        //df
        ds.writeStream
            .outputMode("update")
            .format("console")
            .start
            .awaitTermination()
        // TODO: 直接执行SQL
    }
}

case class People(name: String, age: Long, sex: String)