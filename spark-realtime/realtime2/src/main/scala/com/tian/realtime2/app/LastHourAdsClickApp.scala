package com.tian.realtime2.app

import com.tian.realtime1.bean.AdsInfo
import com.tian.realtime1.util.RedisUtil
import org.apache.spark.streaming.{Minutes, Seconds}
import org.apache.spark.streaming.dstream.DStream
import org.json4s.jackson.JsonMethods
import redis.clients.jedis.Jedis

/**
 * @author tian
 * @date 2019/9/28 19:32
 * @version 1.0.0
 */
object LastHourAdsClickApp {
    /**
     * 最近一小时广告点击量实时统计
     *
     * @param adsInfoDStream 从kafka消费到的用户点击数据
     */
    def statLastHourClick(adsInfoDStream: DStream[AdsInfo]): Unit = {
        val adsInfoDStreamWithWindow: DStream[AdsInfo] = adsInfoDStream
            .window(Minutes(60), Seconds(5)) //开窗，窗口长度1小时，滑动步长5秒
        val adsAndHMCountDStream: DStream[(String, List[(String, Int)])] = adsInfoDStreamWithWindow
            .map(adsInfo => ((adsInfo.adsId, adsInfo.hmString), 1)) //((广告，详细时间), 1)
            .reduceByKey(_ + _)
            .map {
                case ((ads, hm), count) => (ads, (hm, count))
            }
            .groupByKey()
            .map { //按照hh:MM升序排列
                case (ads, it) => (ads, it.toList.sortBy(_._1))
            }
        adsAndHMCountDStream
            .foreachRDD(it => {
                val client: Jedis = RedisUtil.getJedisClient
                it.foreach {
                    case (ads, hmCountList) =>
                        import org.json4s.JsonDSL._
                        val v: String = JsonMethods.compact(JsonMethods.render(hmCountList))
                        client.hset("last_hour_click", ads, v)
                }
                client.close()
            })
    }
}
