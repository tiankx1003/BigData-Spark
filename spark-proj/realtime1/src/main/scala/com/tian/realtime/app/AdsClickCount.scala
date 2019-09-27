package com.tian.realtime.app

import com.tian.realtime.bean.AdsInfo
import com.tian.realtime.util.RedisUtil
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{Dataset, SparkSession}
import redis.clients.jedis.Jedis

/**
 * @author tian
 * @date 2019/9/27 11:29
 * @version 1.0.0
 */
object AdsClickCount {
    def statClickCount(spark: SparkSession, filteredAdsInfoDS: Dataset[AdsInfo]) = {
        val resultDF = filteredAdsInfoDS
            .groupBy("dayString", "area", "city", "adsId")
            .count()
        resultDF
            .writeStream
            .outputMode("complete")
            .foreachBatch((df, bachId) => {
                df.persist()
                if (df.count() > 0) {
                    df.foreachPartition(rowIt => {
                        val client = RedisUtil.getJedisClient
                        var dayString = ""
                        val hashValue = rowIt.map(row => {
                            dayString = row.getString(0)
                            val area = row.getString(1)
                            val city = row.getString(2)
                            val adsId = row.getString(3)
                            val count = row.getLong(4)
                            (s"$area:$city:$adsId", count.toString)
                        }).toMap
                        import scala.collection.JavaConversions._
                        if (hashValue.nonEmpty)
                            client.hmset(s"date:area:city:ads:$dayString", hashValue)
                        client.close()
                    })
                }
                df.unpersist()
            })
            .trigger(Trigger.ProcessingTime(2000))
            .start()
            .awaitTermination()
    }
}
