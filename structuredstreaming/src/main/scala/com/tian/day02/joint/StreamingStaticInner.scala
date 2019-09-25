package com.tian.day02.joint

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * 流式数据和静态数据内连接
 *
 * @author tian
 * @date 2019/9/25 14:01
 * @version 1.0.0
 */
object StreamingStaticInner {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("StreamingStatic")
            .getOrCreate()
        import spark.implicits._
        val arr: Array[(String, Int)] = Array(("ls", 20), ("ww", 10), ("zs", 15))
        //静态df
        val staticDF: DataFrame = spark.sparkContext.parallelize(arr).toDF("name", "age")
        //动态df
        val streamingDF: DataFrame = spark.readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .load
            .as[String]
            .map(line => {
                val splits = line.split(",")
                (splits(0), splits(1))
            })
            .toDF("name", "sex")
        //内连接
        val joinedDF: DataFrame = streamingDF.join(staticDF, Seq("name")) //两者的字段名不同会报异常
        val rightJoinedDF = streamingDF.join(staticDF, Seq("name")) //内连接
        joinedDF.writeStream //静态和流连接后为流数据
            .format("console")
            .outputMode("update")
            .start()
            .awaitTermination()
    }
}
