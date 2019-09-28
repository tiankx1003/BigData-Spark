package com.tian.realtime2

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

import com.tian.realtime1.bean.AdsInfo
import com.tian.realtime2.util.MyKafkaUtil
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author tian
 * @date 2019/9/28 19:06
 * @version 1.0.0
 */
object RealtimeApp {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setMaster("local[*]").setAppName("HighKafka")
        val ssc = new StreamingContext(conf, Seconds(5))
        ssc.checkpoint("file/ck-ss1") //设置检查点
        val dayFormatter = new SimpleDateFormat("yyyy-MM-dd")
        val hmFormatter = new SimpleDateFormat("HH:mm")
        val adsInfoDStream = MyKafkaUtil
            .getKafkaStream(ssc, "topic1")
            .map(record => {
                val arr = record.value.split(",")
                val date = new Date(arr(0).toLong)
                AdsInfo(
                    arr(0).toLong,
                    new Timestamp(arr(0).toLong),
                    dayFormatter.format(date),
                    hmFormatter.format(date),
                    arr(1),
                    arr(2),
                    arr(3),
                    arr(4)
                )
            })

        ssc.start()
        ssc.awaitTermination()
    }
}
