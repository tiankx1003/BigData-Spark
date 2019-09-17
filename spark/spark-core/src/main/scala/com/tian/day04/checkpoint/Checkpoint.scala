package com.tian.day04.checkpoint

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/17 11:13
 * @version 1.0.0
 */
/*
cache
会使用已经计算好的结果直接做缓存，只做一次计算
仍然记录血缘关系

checkpoint
会启动一个新的job，来做checkpoint，会做两次计算
会切断血缘关系

checkpoint和cache结合使用
可以实现只做一次计算，而且可以记录血缘关系
 */
object Checkpoint {
    def main(args: Array[String]): Unit = {
        System.setProperty("HADOOP_USER_NAME","tian") //也可在环境变量设置
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        //sc.setCheckpointDir("path")
        sc.setCheckpointDir("hdfs://hadoop102:9000/checkpoint")
        val rdd1 =
            sc.parallelize(List("hadoop", "scala", "hello", "hello", "scala", "world", "hello", "scala"), 2)
        val rdd2 = rdd1.map(x => {
            println(System.currentTimeMillis()) //打印时间戳用于验证
            (x, System.currentTimeMillis())
        }).filter(x => true)
        val result = rdd2.map((_, 1))
        rdd2.cache() //先缓存，这样checkpoint就不会做两次计算了
        rdd2.checkpoint() //针对rdd4做checkpoint
        result.collect.foreach(println) //会立即启动一个job用于做checkpoint
        println("---------------------")
        result.collect.foreach(println) //如果没有cache，和前面打印的时间戳不同，之后的时间戳都与之相同
        result.collect.foreach(println)
        result.collect.foreach(println)
        Thread.sleep(10000)
        sc.stop()
    }
}
