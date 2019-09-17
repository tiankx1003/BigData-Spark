package com.tian.day04.mysql

import java.sql.DriverManager

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/17 20:55
 * @version 1.0.0
 */
object MySQLWrite {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val driver = "com.mysql.jdbc.Driver"
        val url = "jdbc:mysql://hadoop102:3306/rdd"
        val userName = "root"
        val passWD = "root"
        val rdd = sc.parallelize(Array(3, 5, 6, 7, 12, 9, 2))
        val sql = "insert into user values(?)"
        rdd.foreachPartition(it => {
            Class.forName(driver)
            val conn = DriverManager.getConnection(url, userName, passWD)
            val ps = conn.prepareStatement(sql)
            it.foreach(x => {
                ps.setInt(1, x)
                ps.addBatch()
            })
            ps.executeBatch()
            ps.close()
            conn.close()
        })
        sc.stop()
    }
}
