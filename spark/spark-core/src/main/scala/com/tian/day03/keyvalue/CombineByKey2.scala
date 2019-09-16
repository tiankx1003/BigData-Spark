package com.tian.day03.keyvalue

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/16 11:39
 * @version 1.0.0
 */
object CombineByKey2 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 =
            sc.parallelize(List(("a", 2), ("a", 2), ("c", 4), ("b", 3), ("c", 6), ("c", 8)), 2)
        val rdd2 = rdd1.combineByKey(
            v => (v, 1),
            (sumCount: (Int, Int), v: Int) => (sumCount._1 + v, sumCount._2 + v),
            (sumCount1: (Int, Int), sumCount2: (Int, Int)) => (sumCount1._1 + sumCount2._1, sumCount1._2 + sumCount2._2)
        ).mapValues(sumCount => sumCount._1.toDouble / sumCount._2)
        rdd2.collect.foreach(println)
        sc.stop()
    }
}
