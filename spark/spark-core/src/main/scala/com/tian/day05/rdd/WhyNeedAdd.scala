package com.tian.day05.rdd

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/18 9:09
 * @version 1.0.0
 */
object WhyNeedAdd {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array(3, 5, 8, 2))
        var a = 1
        val result = rdd1.map(x => {
            a += 1
            println(a)
            (a, 1)
        })
        result.collect
        println("-----------")
        //无法修改共享变量
        println(a) //1，分布式运行，driver发给executor的是a的值，在executor运算和driver的值无关
        sc.stop()
    }
}
