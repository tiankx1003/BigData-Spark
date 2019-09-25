package com.tian.day02.joint

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.Trigger

/**
 * 流式数据和静态数据外连接
 *
 * @author tian
 * @date 2019/9/25 18:40
 * @version 1.0.0
 */
object StreamingStaticOuter {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("StreamingStaticOuter")
            .getOrCreate()
        import spark.implicits._
        val arr: Array[(String, Int)] = Array(("lis", 20), ("zs", 21), ("ww", 15))
        val staticDF = spark.sparkContext.parallelize(arr).toDF("name", "age")
        val streamingDF = spark
            .readStream
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
        //外连接，可传入连接类型，流式数据必须在左侧
        val joinedDF = streamingDF.join(staticDF, Seq("name"), "left")
        joinedDF
            .writeStream
            .outputMode("update")
            .format("console")
            .trigger(Trigger.ProcessingTime(0))
            .start()
            .awaitTermination()
    }
}
