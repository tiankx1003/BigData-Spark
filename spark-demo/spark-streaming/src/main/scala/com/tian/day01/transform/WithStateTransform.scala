package com.tian.day01.transform

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * 带状态转换
 *
 * @author tian
 * @date 2019/9/23 20:12
 * @version 1.0.0
 */
object WithStateTransform {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("WithStateTransform")
        val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))
        ssc.checkpoint("./ck1") //设置检查点
        // ssc.sparkContext.setCheckpointDir("./ck1") //效果同上
        val socketSteam: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop102", 9999)
        val resultDStream: DStream[(String, Int)] = socketSteam
            .flatMap(_.split(" "))
            .map((_, 1))
            .updateStateByKey( // TODO 传入函数对应参数的概念
                (seq: Seq[Int], opt: Option[Int]) => Some(seq.sum + opt.getOrElse(0))
            )
        resultDStream.print()
        ssc.start()
        ssc.awaitTermination()
    }
}
