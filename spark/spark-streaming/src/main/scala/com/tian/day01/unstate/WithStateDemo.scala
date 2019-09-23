package com.tian.day01.unstate

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}

/**
 * @author tian
 * @date 2019/9/23 16:41
 * @version 1.0.0
 */
object WithStateDemo {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("TransformDemo")
        val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))
        ssc.checkpoint(".ck1")
        //ssc.sparkContext.setCheckpointDir(".ck1") //效果同上
        val socketStream: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop102", 9999)
        val resultDStream: DStream[(String, Int)] = socketStream
            .flatMap(_.split(" "))
            .map((_, 1))
            .updateStateByKey(
                (seq: Seq[Int], opt: Option[Int]) => Some(seq.sum + opt.getOrElse(0))
            )
        resultDStream.print
        ssc.start()
        ssc.awaitTermination()
    }
}
