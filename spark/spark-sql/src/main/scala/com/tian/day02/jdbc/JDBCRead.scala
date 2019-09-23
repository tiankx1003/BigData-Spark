package com.tian.day02.jdbc

import java.util.Properties

import org.apache.spark.sql.SparkSession

/**
 * @author tian
 * @date 2019/9/21 9:10
 * @version 1.0.0
 */
object JDBCRead {
    def main(args: Array[String]): Unit = {
        val spark = SparkSession
            .builder()
            .master("local[*]")
            .appName("JDBCRead")
            .getOrCreate()
        //通用读操作
        val df1 = spark.read.format("jdbc")
            .option("url", "jdbc:mysql://hadoop102:3306/rdd")
            .option("user", "root")
            .option("password", "root")
            .option("dbtable", "user")
            .load()
        df1.show()
        df1.createTempView("user")
        spark.sql("select * from user where id > 10").show(10)
        //专用读操作
        val props = new Properties()
        props.setProperty("user", "root")
        props.setProperty("password", "root")
        val df2 =
            spark.read.jdbc("jdbc:mysql://hadoop102:3306/rdd", "user", props)
        df2.show
        spark.close()
    }
}
