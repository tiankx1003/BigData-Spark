package com.tian.day01

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
 * @author tian
 * @date 2019/9/20 15:14
 * @version 1.0.0
 */
object CreateDF {
    def main(args: Array[String]): Unit = {
        val spark = SparkSession
            .builder()
            .master("local[2]")
            .appName("RDD2DF")
            .getOrCreate()
        val rdd1 =
            spark.sparkContext.parallelize(Array(("Tom", 22, "male"), ("Jack", 21, "female")))
        val rowRDD = rdd1.map {
            case (name, age, sex) => Row(name, age, sex)
        }
        val st =
            StructType(StructField("name", StringType) :: StructField("age", IntegerType) :: StructField("sex", StringType) :: Nil)
        val df = spark.createDataFrame(rowRDD, st)
    }
}

