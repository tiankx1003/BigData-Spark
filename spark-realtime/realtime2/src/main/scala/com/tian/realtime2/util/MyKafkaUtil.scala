package com.tian.realtime2.util

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}

/**
 * @author tian
 * @date 2019/9/28 19:05
 * @version 1.0.0
 */
object MyKafkaUtil {
    val kafkaParams = Map[String, Object](
        "bootstrap.servers" -> "hadoop102:9092,hadoop103:9092,hadoop104:9092",
        "key.deserializer" -> classOf[StringDeserializer],
        "value.deserializer" -> classOf[StringDeserializer],
        "group.id" -> "bigdata",
        "auto.offset.reset" -> "latest",
        "enable.auto.commit" -> (true: java.lang.Boolean)
    )


    def getKafkaStream(ssc:StreamingContext, topic: String): InputDStream[ConsumerRecord[String, String]] = {
        KafkaUtils.createDirectStream(
            ssc,
            LocationStrategies.PreferConsistent,
            ConsumerStrategies.Subscribe[String, String](Array(topic), kafkaParams)
        )
    }

}
