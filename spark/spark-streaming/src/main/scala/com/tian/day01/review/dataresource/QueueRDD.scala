package com.tian.day01.review.dataresource

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}
import scala.collection.mutable

/**
 * RDD队列数据源
 *
 * @author tian
 * @date 2019/9/23 20:08
 * @version 1.0.0
 */
object QueueRDD {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("QueueRDD")
        val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))
        val rddQueue: mutable.Queue[RDD[Int]] = mutable.Queue[RDD[Int]]()
        //val resultDStream: DStream[Int] = ssc.queueStream(rddQueue).reduce(_ + _) //每次处理一个RDD
        // @param oneAtATime Whether only one RDD should be consumed from the queue in every interval
        val resultDStream: DStream[Int] = ssc.queueStream(rddQueue, oneAtATime = false).reduce(_ + _)
        resultDStream.print(100)
        ssc.start()
        while (true) { //每个循环创建一个RDD
            val sc: SparkContext = ssc.sparkContext
            rddQueue.enqueue(sc.parallelize(1 to 100))
            Thread.sleep(1000) //每个时间间隔内有三个RDD，
            // 不设置sleep可以给集群做压测，即每个时间间隔内可以创建和处理多少个RDD
        }
        ssc.awaitTermination()
    }
}
