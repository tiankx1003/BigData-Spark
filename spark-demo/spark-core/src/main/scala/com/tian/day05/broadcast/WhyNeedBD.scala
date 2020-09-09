package com.tian.day05.broadcast

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 广播变量
 * 不是直接发给每个task线程，而是直接发到Executor上
 * 极大的优化了内存的占用
 *
 * @author tian
 * @date 2019/9/18 10:38
 * @version 1.0.0
 */
object WhyNeedBD {
    val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val rdd1 = sc.parallelize(Array(20, 30, 40, 50, 20, 50), 4)
    val set: Set[Int] = Set(10, 20) //同一进程的每个task线程都会有一个set集合，set只有读操作(没有线程安全问题)
    val bd: Broadcast[Set[Int]] = sc.broadcast(set) //发送广播变量到executor上，task线程共享set
    rdd1.foreach(x => println(set.contains(x)))
    rdd1.foreach(x => {
        val set = bd.value //取出广播变量中存储的值
        println(set.contains(x))
    })
    sc.stop()
}
