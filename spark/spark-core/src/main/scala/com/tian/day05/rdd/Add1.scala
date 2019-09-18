package com.tian.day05.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 累加器
 * 累加器实现了共享变量的修改
 * 累加器只在行动算子中使用，不在转换算子中使用
 *
 * @author tian
 * @date 2019/9/18 9:14
 * @version 1.0.0
 */
object Add1 {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc: SparkContext = new SparkContext(conf)
        val rdd1: RDD[Int] = sc.parallelize(Array(3, 6, 4, 8))
        val acc: LongAccumulator = sc.longAccumulator //累加器实现了共享变量的修改
        val result: RDD[(Int, Int)] = rdd1.map(x => {
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
