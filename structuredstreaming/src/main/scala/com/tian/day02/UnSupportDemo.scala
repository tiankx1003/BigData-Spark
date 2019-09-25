package com.tian.day02

import org.apache.spark.sql.SparkSession

/**
 * 不支持的操作
 *
 * @author tian
 * @date 2019/9/25 20:21
 * @version 1.0.0
 */
/*
1.	多个Streaming 聚合(例如在 DF 上的聚合链)目前还不支持
2.	limit 和取前 N 行还不支持
3.	distinct 也不支持
4.	仅仅支持对 complete 模式下的聚合操作进行排序操作
5.	仅支持有限的外连接
6.	有些方法不能直接用于查询和返回结果, 因为他们用在流式数据上没有意义.
count() 不能返回单行数据, 必须是s.groupBy().count()
foreach() 不能直接使用, 而是使用: ds.writeStream.foreach(...)
show() 不能直接使用, 而是使用 console sink
如果执行上面操作会看到这样的异常: operation XYZ is not supported with streaming DataFrames/Datasets.
 */
object UnSupportDemo {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("UnSupportDemo")
            .getOrCreate()
        import spark.implicits._
        spark
            .readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .load
            .as[String]
            .toDF("v")
            .createOrReplaceTempView("user")
        spark.sql( //只能输出第一次的内容
            """
              |select *
              |from user
              |limit 1
              |""".stripMargin)
            .writeStream
            .outputMode("append")
            .format("console")
            .start()
            .awaitTermination()
    }
}
