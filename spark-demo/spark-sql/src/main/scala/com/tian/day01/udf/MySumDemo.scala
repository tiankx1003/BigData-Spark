package com.tian.day01.udf

import org.apache.spark.sql.SparkSession

/**
 * @author tian
 * @date 2019/9/20 16:47
 * @version 1.0.0
 */
object MySumDemo {
    def main(args: Array[String]): Unit = {
        val spark = SparkSession
            .builder()
            .master("local[*]")
            .appName("MySumDemo")
            .getOrCreate()
        //注册
        spark.udf.register("mySum", new MySum)
        val df = spark.read.json(ClassLoader.getSystemResource("employees.json").getPath)
        df.createOrReplaceTempView("user")
        spark.sql("select mySum(age) sum from user").show
        spark.close()
    }
}
