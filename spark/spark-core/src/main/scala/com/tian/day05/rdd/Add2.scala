package com.tian.day05.rdd

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/18 9:32
 * @version 1.0.0
 */
object Add2 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array(3, 6, 4, 8))
        val acc = new MyAcc //自定义累加器
        sc.register(acc) //向executor注册累加器
        val result = rdd1.map(x => {
            acc.add(1)
            (x, 1)
        })
        result.collect
        println("------------")
        println(acc.value) //4
        result.collect
        println("------------")
        println(acc.value) //8，累加器只有一个
        sc.stop()
    }
}
