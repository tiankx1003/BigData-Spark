package com.tian.day04.mysql

import java.sql.DriverManager

import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/17 20:54
 * @version 1.0.0
 */
object MySQLRead {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        //定义连接MySQL的参数
        val driver = "com.mysql.jdbc.Driver"
        val url = "jdbc:mysql://hadoop102:3306/rdd"
        val userName = "root"
        val passWD = "root"
        val rdd = new JdbcRDD(
            sc,
            () => {
                Class.forName(driver)
                DriverManager.getConnection(url, userName, passWD)
            },
            "select * from user where ? <= id and id <= ?",
            20,
            60,
            2,
            result => result.getInt(1)
        )
        rdd.collect.foreach(println)
        sc.stop()
    }
}
