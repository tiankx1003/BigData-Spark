package com.tian.day01.udf

import org.apache.spark.sql.SparkSession

/**
 * @author tian
 * @date 2019/9/20 19:55
 * @version 1.0.0
 */
object MyAvgDemo {
    def main(args: Array[String]): Unit = {
        val spark = SparkSession
            .builder()
            .master("local[*]")
            .appName("MyAvgDemo")
            .getOrCreate()
        //注册
        spark.udf.register("myAvg", new MyAvg)
        val df = spark.read.json(ClassLoader.getSystemResource("employees.json").getPath)
        df.createOrReplaceTempView("user")
        spark.sql("select myAvg(age) avg from user").show
        spark.close()
    }
}
