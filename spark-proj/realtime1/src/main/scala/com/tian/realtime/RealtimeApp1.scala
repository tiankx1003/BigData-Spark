package com.tian.realtime

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

import com.tian.realtime.app.{AdsClickCount, BlackList}
import com.tian.realtime.bean.AdsInfo
import org.apache.spark.sql.{Dataset, SparkSession}

/**
 * @author tian
 * @date 2019/9/27 9:12
 * @version 1.0.0
 */
/*
redis-cli --raw
keys *
hgetall value
 */
object RealtimeApp1 {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[*]")
            .appName("RealtimeApp1")
            .getOrCreate()
        import spark.implicits._
        val dayFormatter: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
        val hmFormatter: SimpleDateFormat = new SimpleDateFormat("HH:mm")
        //从Kafka消费到的数据
        val adsInfoDS: Dataset[AdsInfo] = spark.readStream
            .format("kafka")
            .option("kafka.bootstrap.servers", "hadoop102:9092,hadoop103:9092,hadoop104:9092")
            .option("subscribe", "topic1")
            .load
            .select("value")
            .as[String]
            .map(line => {
                val arr: Array[String] = line.split(",")
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
        val filteredAdsInfoDS: Dataset[AdsInfo] = BlackList.statBlackList(spark, adsInfoDS)
        AdsClickCount.statClickCount(spark,filteredAdsInfoDS)
    }
}
