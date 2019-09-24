package com.tian.day01.transform

import org.apache.spark.SparkConf
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
        val conf = new SparkConf().setMaster("local[2]").setAppName("WithStateTransform")
        val ssc = new StreamingContext(conf, Seconds(3))
        ssc.checkpoint(".ck1") //设置检查点
        // ssc.sparkContext.setCheckpointDir(".ck1") //效果同上
        val socketSteam = ssc.socketTextStream("hadoop102", 9999)
        val resultDStream = socketSteam
            .flatMap(_.split(" "))
            .map((_, 1))
            .updateStateByKey(
                (seq: Seq[Int], opt: Option[Int]) => Some(seq.sum + opt.getOrElse(0))
            )
        resultDStream.print()
        ssc.start()
        ssc.awaitTermination()
    }
}
