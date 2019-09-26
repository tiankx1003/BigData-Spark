package com.tian.duplicate.join

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * Streaming join Static
 *
 * @author tian
 * @date 2019/9/26 15:33
 * @version 1.0.0
 */
object StreamingStatic {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("StreamingStatic")
            .getOrCreate()
        import spark.implicits._
        val arr: Array[(String, Int)] = Array(("ls", 20), ("ww", 21), ("zs", 23))
        val staticDF: DataFrame = spark.sparkContext.parallelize(arr).toDF("name", "age")
        val streamingDF: DataFrame = spark
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
        val innerJoinedDF: DataFrame = streamingDF.join(staticDF, Seq("name")) //两者连接条件的字段名必须相同
        val outerJoinedDF: DataFrame = streamingDF.join(staticDF, Seq("name"), "left") //外连接，传入连接类型
        //外连接时左侧必须是流式数据
        innerJoinedDF
            .writeStream
            .outputMode("update")
            .format("console")
            .start()
            .awaitTermination()
        spark.stop()
    }
}
