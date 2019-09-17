package com.tian.day04.cache

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/17 10:30
 * @version 1.0.0
 */
object CacheDemo1 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 =
            sc.parallelize(List("hadoop", "scala", "hello", "hello", "scala", "world", "hello", "scala"), 2)
        val rdd2 = rdd1.map(x => {
            println(x)
            (x, 1)
        }).filter(x => true)
        val result = rdd2.map((_, 1))
        result.collect
        println("-------------------")
        result.collect //每次都会从最初重新计算，所以会再输出一次
        rdd2.cache() //把计算结果放入缓存
        rdd2.persist() //可以传入不同的存储级别，cache()就是调用了persist使用默认参数(存储级别)
        println("-------------------")
        result.collect //结果已经放入缓存，不需要重新计算，所以不会输出
        Thread.sleep(100000)
        sc.stop()
        /*
        cache()会占用内存
         */
    }
}
