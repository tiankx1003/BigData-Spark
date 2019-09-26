package com.tian.output

import java.util.Properties

import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * ForeachBatch Sink
 * 只能用于输出批处理的数据
 *
 * @author tian
 * @date 2019/9/26 17:04
 * @version 1.0.0
 */
object ForeachBatchSink {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("ForeachBatchSink")
            .getOrCreate()
        val lines: DataFrame = spark
            .readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .load
        import spark.implicits._
        val wordCount: DataFrame = lines
            .as[String]
            .flatMap(_.split("\\W+"))
            .groupBy("value")
            .count()
        val props: Properties = new Properties()
        props.setProperty("user", "root")
        props.setProperty("password", "aaa")
        val query: StreamingQuery = wordCount
            .writeStream
            .outputMode("complete")
            .foreachBatch((df, batchId) => { //当前分区和当前批次id
                if (df.count() != 0) {
                    df.cache()
                    df.write.json(s"./$batchId")
                    df.write
                        .mode("overwrite")
                        .jdbc("jdbc:mysql://hadoop102:8086", "word_count", props)
                }
            })
            .start()
        query.awaitTermination()
        spark.stop()
    }
}

// TODO: 有报错