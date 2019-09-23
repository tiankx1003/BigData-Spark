package com.tian.day01.kafka

import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaCluster, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.kafka.KafkaCluster.Err

/**
 * Kafka高级API
 *
 * @author tian
 * @date 2019/9/23 14:41
 * @version 1.0.0
 */
object WordCount3 {
    val brokers: String = "hadoop102:9092,hadoop103:9092,hadoop104:9092"
    val topic: String = "first"
    val group: String = "bigdata"
    val kafkaParams: Map[String, String] =
        Map(ConsumerConfig.GROUP_ID_CONFIG -> group, ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> brokers)
    //手动提交offsets，读取offsets需要使用
    val kafkaCluster: KafkaCluster = new KafkaCluster(kafkaParams)

    //读取offsets
    def readOffsets(): Map[TopicAndPartition, Long] = { //泛型为分区和偏移量
        var resultMap: Map[TopicAndPartition, Long] = Map[TopicAndPartition, Long]()
        //获取到所有分区
        val topicAndPartitionEither: Either[Err, Set[TopicAndPartition]] = kafkaCluster.getPartitions(Set(topic))
        topicAndPartitionEither match {
            case Right(topicAndPartitionSet) => //分区存在
                //获取分区和偏移量
                val topicAndPartitionOffsetEither: Either[Err, Map[TopicAndPartition, Long]] =
                    kafkaCluster.getConsumerOffsets(group, topicAndPartitionSet)
                if (topicAndPartitionOffsetEither.isRight) //表示曾经消费过，已经有offset
                    resultMap ++= topicAndPartitionOffsetEither.right.get
                else //分区存在，但是没有map，表示第一次消费分区，把每个分区的偏移量置零
                    topicAndPartitionSet.foreach(tap => resultMap += tap -> 0L)
            case _ =>
        }
        resultMap
    }

    def writeOffsets(sourceDStream: InputDStream[String]): Unit = {
        sourceDStream.foreachRDD(rdd => { //每个时间间隔都会遍历一次
            var map: Map[TopicAndPartition, Long] = Map[TopicAndPartition, Long]()
            //强转成HasOffsetRanges，包含本次消费的offset其实范围
            val hasOffsetRanges: HasOffsetRanges = rdd.asInstanceOf[HasOffsetRanges]
            val ranges: Array[OffsetRange] = hasOffsetRanges.offsetRanges
            ranges.foreach(rang => { //每个分区都会遍历一次
                val offset: Long = rang.untilOffset
                map += rang.topicAndPartition() -> offset
            })
            kafkaCluster.setConsumerOffsets(group, map)
        })
    }

    def main(args: Array[String]): Unit = { // TODO: 视频
        val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("HighKafka")
        val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))
        val offsets: Map[TopicAndPartition, Long] = readOffsets()
        val sourceDStream: InputDStream[String] =
            KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, String](
                ssc,
                kafkaParams,
                offsets,
                (mm: MessageAndMetadata[String, String]) => mm.message()
            )
        sourceDStream.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).print(100)
        writeOffsets(sourceDStream)
        ssc.start()
        ssc.awaitTermination()
    }
}
