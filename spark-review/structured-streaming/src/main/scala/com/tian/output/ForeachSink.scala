package com.tian.output

import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.{DataFrame, ForeachWriter, Row, SparkSession}

/**
 * 遍历表中的每一行, 允许将流查询结果按开发者指定的逻辑输出
 *
 * @author tian
 * @date 2019/9/26 16:36
 * @version 1.0.0
 */
/*
create database ss;
use ss;
create table word_count(
    word varchar(255) primary key not null,
    count bigint not null);
 */
object ForeachSink {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("ForeachSink")
            .getOrCreate()
        val lines: DataFrame = spark
            .readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .load
        import spark.implicits._
        val wordCount = lines
            .as[String]
            .flatMap(_.split("\\W+"))
            .groupBy("value")
            .count()
        val query: StreamingQuery = wordCount
            .writeStream
            .outputMode("update")
            //把wordcount结果写入到MySQL数据库
            .foreach(new ForeachWriter[Row] {
                var conn: Connection = _
                var ps: PreparedStatement = _
                var batchCount: Int = 0

                override def open(partitionId: Long, epochId: Long): Boolean = {
                    println("open... " + partitionId + " " + epochId)
                    Class.forName("com.mysql.jdbc.Driver")
                    conn = DriverManager.getConnection("jdbc:mysql://hadoop102:3306/ss", "root", "root")
                    val sql: String = "insert into word_count values(?,?) on duplicate key update word=?, count=?"
                    ps = conn.prepareStatement(sql)
                    conn != null && !conn.isClosed && ps != null
                }

                override def process(value: Row): Unit = {
                    println("process... " + value)
                    val word: String = value.getString(0)
                    val count: Long = value.getLong(1)
                    ps.setString(1, word)
                    ps.setLong(2, count)
                    ps.setString(3, word)
                    ps.setLong(4, count)
                    ps.execute()
                }

                override def close(errorOrNull: Throwable): Unit = {
                    println("close...")
                    ps.close()
                    conn.close()
                }
            })
            .start()
        query.awaitTermination()
        spark.stop()
    }
}
