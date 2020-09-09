package com.tian.day02.window

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * reduceByKeyAndWindow计算指定时间区间的结果
 * 窗口长度和滑动步长是周期的整数倍
 * reduceByKeyAndWindow(reduceFunc: (V, V) => V, invReduceFunc: (V, V) => V, windowDuration: Duration, [slideDuration: Duration])
 *
 * @author tian
 * @date 2019/9/24 8:45
 * @version 1.0.0
 */
object Window1 {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("")
        val ssc: StreamingContext = new StreamingContext(conf, Seconds(4)) //滑动步长为4
        val sourceDStream: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop102", 9999)
        sourceDStream
            .flatMap(_.split(" "))
            .map((_, 1))
            //传入计算规则，窗口长度和滑动步长，不设置滑动步长时默认为周期
            // .reduceByKeyAndWindow((_: Int) + (_: Int), Seconds(12))
            .reduceByKeyAndWindow((_: Int) + (_: Int), Seconds(12), Seconds(8)) //添加滑动步长需要指定泛型
            /*
            def reduceByKeyAndWindow(
              reduceFunc: (V, V) => V,
              invReduceFunc: (V, V) => V,
              windowDuration: Duration,
              slideDuration: Duration = self.slideDuration,
              numPartitions: Int = ssc.sc.defaultParallelism,
              filterFunc: ((K, V)) => Boolean = null
            ): DStream[(K, V)]
             */
            // TODO: 视频
            /*.reduceByKeyAndWindow(
                (_: Int) + (_: Int),
                (_: Int) - (_: Int),
                Seconds(12),
                Seconds(8),
                filterFunc = (str: String, a: Int) => false
            )*/
            .print(100)
        ssc.start()
        ssc.awaitTermination()
    }
}
