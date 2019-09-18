package com.tian.day05.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/18 10:07
 * @version 1.0.0
 */
class Add3 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array(20, 30, 40, 50, 20, 50))
        val acc = new MapAcc
        sc.register(acc)
        //sum avg max min
        //一次遍历，同时计算所有指标，计算结果存在一个map
        val result = rdd1.map(x => acc.add(x))
        result.collect
        println(acc.value)
        sc.stop()
    }
}
