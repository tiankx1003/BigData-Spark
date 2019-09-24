package com.tian.day01.dataresource.kafka

import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaCluster, KafkaUtils}

/**
 * Kafka数据源高级API
 *
 * @author tian
 * @date 2019/9/23 20:14
 * @version 1.0.0
 */
object KafkaDStream3 {
    // TODO: 验证
    val brokers = "hadoop102:9092,hadoop103:9092,hadoop104:9092"
    val topic = "first"
    val group = "bigdata"
    val kafkaParams: Map[String, String] =
        Map(ConsumerConfig.GROUP_ID_CONFIG -> group, ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> brokers)
    //手动提交offsets，读取offsets需要使用
    val kafkaCluster = new KafkaCluster(kafkaParams)

    def readOffsets(): Map[TopicAndPartition, Long] = {
        var resultMap = Map[TopicAndPartition, Long]()
        //获取到所有分区
        val topicAndPartitions = kafkaCluster.getPartitions(Set(topic))
        topicAndPartitions match {
            case Right(topicAndPartitionSet) => //分区存在时
                //获取分区和偏移量
                val topicAndPartitionOffsetEither = kafkaCluster.getConsumerOffsets(group, topicAndPartitionSet)
                if (topicAndPartitionOffsetEither.isRight)
                    resultMap ++= topicAndPartitionOffsetEither.right.get
                else
                    topicAndPartitionSet.foreach(tap => resultMap += tap -> 0L)
            case _ => //分区不存在时
        }
        resultMap
    }

    def writeOffsets(sourceDStream: InputDStream[String]): Unit = {
        sourceDStream.foreachRDD(rdd => { //每个时间间隔都会遍历一次
            var map = Map[TopicAndPartition, Long]()
            //转型成HasOffsetRanges，包含本次消费的offset其实范围
            val hasOffsetRanges = rdd.asInstanceOf[HasOffsetRanges]
            val ranges = hasOffsetRanges.offsetRanges
            ranges.foreach(rang => { //每个分区遍历一次
                val offset = rang.untilOffset
                map += rang.topicAndPartition() -> topic
            })
            kafkaCluster.setConsumerOffsets(group, map)
        })
    }

    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setMaster("local[*]").setAppName("HighKafka")
        val ssc = new StreamingContext(conf, Seconds(3))
        val offsets = readOffsets()
        val sourceDStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, String](
            ssc,
            kafkaParams,
            offsets,
            (mm: MessageAndMetadata[String, String]) => mm.message()
        )
        sourceDStream
            .flatMap(_.split(" "))
            .map((_, 1))
            .reduceByKey(_ + _)
            .print(100)
        writeOffsets(sourceDStream)
        ssc.start()
        ssc.awaitTermination()
    }
}
