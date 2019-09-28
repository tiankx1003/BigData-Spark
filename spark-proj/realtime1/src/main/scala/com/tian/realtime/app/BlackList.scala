package com.tian.realtime.app

import java.util

import com.tian.realtime.bean.AdsInfo
import com.tian.realtime.util.RedisUtil
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{Dataset, ForeachWriter, Row, SparkSession}
import redis.clients.jedis.Jedis

object BlackList {
    def statBlackList(spark: SparkSession, adsInfoDS: Dataset[AdsInfo]): Dataset[AdsInfo] = {
        import spark.implicits._
        val filteredAdsInfoDS: Dataset[AdsInfo] = adsInfoDS.mapPartitions((adsInfoIt: Iterator[AdsInfo]) => {
            val adsInfoList: List[AdsInfo] = adsInfoIt.toList
            if(adsInfoList.isEmpty){
                adsInfoList.toIterator
            }else{
                val client: Jedis = RedisUtil.getJedisClient
                val blackList: util.Set[String] = client.smembers(s"blacklist:${adsInfoList.head.dayString}")
                adsInfoList.filter(adsInfo => !blackList.contains(adsInfo.userId))
                    .toIterator
            }
        })
        filteredAdsInfoDS.createOrReplaceTempView("adsInfo")
        spark.sql(
            """
              |select dayString, userId
              |from adsInfo
              |group by dayString, userId, adsId
              |having count(1) >= 10
              |""".stripMargin)
            .writeStream
            .outputMode("update")
            .trigger(Trigger.ProcessingTime(2000))
            .foreach(new ForeachWriter[Row] {
                var client: Jedis = _
                override def open(partitionId: Long, epochId: Long): Boolean = {
                    // TODO: 1.5h debug for "var client: Jedis = RedisUtil.getJedisClient"
                    client = RedisUtil.getJedisClient
                    client != null && client.isConnected
                }

                override def process(value: Row): Unit = {
                    val dayString: String = value.getString(0)
                    val userId: String = value.getString(1)
                    client.sadd(s"blacklist:$dayString", userId)
                }

                override def close(errorOrNull: Throwable): Unit = {
                    if (client != null && client.isConnected)
                        client.close()
                }
            })
            .start()
            //.awaitTermination()
        filteredAdsInfoDS
    }
}
