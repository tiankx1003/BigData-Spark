package com.tian.day05.rdd

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 累加器
 *
 * @author tian
 * @date 2019/9/18 9:14
 * @version 1.0.0
 */
object Add1 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array(3, 6, 4, 8))
        val acc = sc.longAccumulator //累加器实现了共享变量的修改
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
