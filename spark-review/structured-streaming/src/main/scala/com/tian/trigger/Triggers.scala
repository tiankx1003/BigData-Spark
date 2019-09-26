package com.tian.trigger

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.Trigger

/**
 * 触发器
 *
 * @author tian
 * @date 2019/9/26 17:11
 * @version 1.0.0
 */
object Triggers {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("Trigger1")
            .getOrCreate()
        spark
            .readStream
            .format("socket")
            .option("host", "hadoop102")
            .option("port", 9999)
            .load
            .writeStream
            .outputMode("append")
            .format("console")
            //不设置trigger时为默认触发器
            //.trigger(Trigger.ProcessingTime("2 seconds")) //微处理触发器
            //.trigger(Trigger.Once()) //只处理一次，处理完毕后自动退出
            .trigger(Trigger.Continuous("1 seconds")) //持续处理
            .start()
            .awaitTermination()
        spark.stop()
    }
}
