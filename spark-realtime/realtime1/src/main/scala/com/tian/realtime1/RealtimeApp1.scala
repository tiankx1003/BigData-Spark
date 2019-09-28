package com.tian.realtime1

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

import com.tian.realtime1.app.{AdsClickCountApp, BlackListApp}
import com.tian.realtime1.bean.AdsInfo
import org.apache.spark.sql.SparkSession

/**
 * @author tian
 * @date 2019/9/28 18:37
 * @version 1.0.0
 */
object RealtimeApp1 {
    def main(args: Array[String]): Unit = {
        val spark = SparkSession
            .builder()
            .master("local[*]")
            .appName("RealtimeApp1")
            .getOrCreate()
        import spark.implicits._
        val dayFormatter = new SimpleDateFormat("yyyy-MM-dd")
        val hmFormatter = new SimpleDateFormat("HH:mm")
        val adsInfoDS = spark
            .readStream
            .format("kafka")
            .option("kafka.bootstrap.servers", "hadoop102:9092,hadoop103:9092,hadoop104:9092")
            .option("subscribe", "topic1")
            .load()
            .select("value")
            .as[String]
            .map(line => {
                val arr = line.split(",")
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
        val filteredAdsInfoDS = BlackListApp.statBlackList(spark,adsInfoDS)
        AdsClickCountApp.statClickCount(spark,filteredAdsInfoDS)
    }
}
