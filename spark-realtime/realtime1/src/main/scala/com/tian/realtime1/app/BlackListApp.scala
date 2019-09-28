package com.tian.realtime1.app

import com.tian.realtime1.bean.AdsInfo
import com.tian.realtime1.util.RedisUtil
import org.apache.spark.sql.{Dataset, ForeachWriter, Row, SparkSession}
import redis.clients.jedis.Jedis

/**
 * @author tian
 * @date 2019/9/28 18:43
 * @version 1.0.0
 */
object BlackListApp {
    def statBlackList(spark: SparkSession, adsInfoDS: Dataset[AdsInfo]) = {
        import spark.implicits._
        val filteredAdsInfoDS = adsInfoDS
            .mapPartitions(it => {
                val adsInfoList = it.toList
                val client = RedisUtil.getJedisClient
                val blackList = client.smembers(s"blacklist:${adsInfoList(0).dayString}")
                adsInfoList.filter(adsInfo => !blackList.contains(adsInfo.userId)).toIterator
            })
        filteredAdsInfoDS.createOrReplaceTempView("adsInfo")
        spark.sql(
            """
              |select dayString, userId
              |from adsInfo
              |group by dayString, userId, adsId
              |having count(*) >= 10
              |""".stripMargin)
            .writeStream
            .outputMode("update")
            .foreach(new ForeachWriter[Row] {
                var client: Jedis = _

                override def open(partitionId: Long, epochId: Long): Boolean = {
                    client = RedisUtil.getJedisClient
                    client != null && client.isConnected
                }

                override def process(value: Row): Unit = {
                    val day = value.getString(0)
                    val userId = value.getString(1)
                    client.sadd(s"blacklist:$day", userId)
                }

                override def close(errorOrNull: Throwable): Unit = {
                    if (client != null && client.isConnected)
                        client.close()
                }
            })
        filteredAdsInfoDS
    }
}
