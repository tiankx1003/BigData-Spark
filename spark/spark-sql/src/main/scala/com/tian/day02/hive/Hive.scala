package com.tian.day02.hive

import org.apache.spark.sql.SparkSession

/**
 * @author tian
 * @date 2019/9/21 11:12
 * @version 1.0.0
 */
object Hive {
    def main(args: Array[String]): Unit = {
        System.setProperty("HADOOP_USER_NAME", "tian")
        val spark = SparkSession
            .builder()
            .master("local[*]")
            .appName("hive")
            .enableHiveSupport() //支持hive
            .config("spark.sql.warehouse.dir", "hdfs://hadoop102:9000/user/hive/warehouse")
            .getOrCreate()
        spark.sql("show databases").show
        spark.sql("create database if not exists spark")
        spark.sql("use spark")
        spark.sql("create table if not exists user(id int,name string) row format delimited by '\\t'")
        spark.sql("select * from user")
        spark.sql("insert into table user values(1,'wangwu')")
        spark.sql("select * from emp").show
        spark.close()
    }
}
