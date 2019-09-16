package com.tian.day03.keyvalue

import org.apache.spark.{SparkConf, SparkContext}

/**
 * sortByKey是整体排序，使用较少
 *
 * @author tian
 * @date 2019/9/16 13:57
 * @version 1.0.0
 */
object SortByKey {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 =
            sc.parallelize(List(("a", 2), ("a", 2), ("c", 4), ("b", 3), ("c", 6), ("c", 8)), 2)
        val rdd2 = rdd1.sortByKey() //默认升序，排序后分区默认为原始分区
        rdd1.sortByKey(false, 4)
        rdd1.sortBy(x => x) //sortBy底层还是调用了sortByKey
        rdd2.collect.foreach(println)
        sc.stop()
    }
}
