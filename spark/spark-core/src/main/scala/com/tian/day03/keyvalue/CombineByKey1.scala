package com.tian.day03.keyvalue

import org.apache.spark.{SparkConf, SparkContext}

/**
 * combineByKey与aggregateByKey相比，零值不写明可经过第一次计算得到
 *
 * @author tian
 * @date 2019/9/16 11:23
 * @version 1.0.0
 */
object CombineByKey1 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 =
            sc.parallelize(List(("a", 2), ("a", 2), ("c", 4), ("b", 3), ("c", 6), ("c", 8)), 2)
        val rdd2 = rdd1.combineByKey( //需要手动指明数据类型
            v => v,
            //v => v + 10 //对每个分区都会加一下
            (last: Int, v: Int) => last + v,
            (v1: Int, v2: Int) => v1 + v2
        )
        rdd2.collect.foreach(println)
        sc.stop()
    }
}
