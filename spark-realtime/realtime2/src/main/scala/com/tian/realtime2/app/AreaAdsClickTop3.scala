package com.tian.realtime2.app

import com.tian.realtime1.bean.AdsInfo
import com.tian.realtime1.util.RedisUtil
import org.apache.spark.streaming.dstream.DStream
import org.json4s.jackson.JsonMethods
import redis.clients.jedis.Jedis

/**
 * @author tian
 * @date 2019/9/28 19:17
 * @version 1.0.0
 */
object AreaAdsClickTop3 {
    /**
     * 每天每地区热门广告top3
     *
     * @param adsInfoDStream 从Kafka消费到的广告点击数据
     */
    def statAreaAdsClick(adsInfoDStream: DStream[AdsInfo]): Unit = {
        val dayAreaAndAdsCountDStream: DStream[((String, String), (String, Int))] = adsInfoDStream
            .map(adsInfo => ((adsInfo.dayString, adsInfo.area, adsInfo.adsId), 1))
            .updateStateByKey((seq: Seq[Int], option: Option[Int]) => { //有状态转换
                Some(seq.sum + option.getOrElse(0))
            })
            .map { //调整数据结构
                case ((day, area, adsId), count) => ((day, area), (adsId, count))
            }
        val dayAreaAdsClickCountTop3: DStream[(String, String, List[(String, Int)])] = dayAreaAndAdsCountDStream
            .groupByKey() //按照日期和地区进行分组
            .map {
                case ((day, area), adsIdCountIterable) => //按照count排序取前三
                    (day, area, adsIdCountIterable.toList.sortBy(-_._2).take(3))
            }
        dayAreaAdsClickCountTop3.foreachRDD(foreachFunc = rdd => { //数据写入到redis
            val client: Jedis = RedisUtil.getJedisClient
            val arr: Array[(String, String, List[(String, Int)])] = rdd.collect
            import org.json4s.JsonDSL._
            import scala.collection.JavaConversions._
            val resultMap: Map[String, String] = arr
                .map {
                    case (day, area, list) => (area, JsonMethods.compact(JsonMethods.render(list)))
                }.toMap
            client.hmset(s"area:ads:top3:${arr(0)._1}", resultMap)
            client.close()
        })
    }
}
