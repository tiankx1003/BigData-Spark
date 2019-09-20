package com.tian.day01

import org.apache.spark.sql.SparkSession

/**
 * @author tian
 * @date 2019/9/20 14:33
 * @version 1.0.0
 */
object DF2RDD {
    def main(args: Array[String]): Unit = {
        //先初始化SparkSession
        val spark = SparkSession
            .builder()
            .master("local[2]")
            .appName("RDD2DF")
            .getOrCreate()
        import spark.implicits._
        val rdd1 =
            spark.sparkContext.parallelize(Array(("Tom", 22, "male"), ("Jack", 21, "female")))
        val df = rdd1.toDF("name", "age", "sex")
        val rdd2 = df.rdd //df转rdd，rdd中存放的是Row
        rdd2.collect.foreach(println)
        rdd2.map(row => {
            row.getString(0) //如果第一个元素类型不是String，会报错
        }).collect.foreach(println)
        spark.close()
    }
}
