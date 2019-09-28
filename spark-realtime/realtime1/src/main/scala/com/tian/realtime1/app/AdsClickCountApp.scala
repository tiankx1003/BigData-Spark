package com.tian.realtime1.app

import com.tian.realtime1.bean.AdsInfo
import com.tian.realtime1.util.RedisUtil
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{Dataset, SparkSession}

/**
 * @author tian
 * @date 2019/9/28 18:51
 * @version 1.0.0
 */
object AdsClickCountApp {
    def statClickCount(spark: SparkSession, filteredAdsInfoDS: Dataset[AdsInfo]) = {
        val resultDF = filteredAdsInfoDS
            .groupBy("dayString", "area", "city", "adsId")
            .count()
        resultDF
            .writeStream
            .foreachBatch((df, bachId) => {
                df.persist()
                if (df.count() > 0) {
                    df.foreachPartition(rowIt => {
                        val client = RedisUtil.getJedisClient
                        var dayString = ""
                        val hashValue = rowIt.map(row => {
                            dayString = row.getString(1)
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
