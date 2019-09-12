package com.tian.day02.doublevalue

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/12 15:20
 * @version 1.0.0
 */
object RDDDoubleValue {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array(20, 30, 40, 50, 20, 50))
        val rdd2 = sc.parallelize(Array(30, 20, 10, 10, 20, 30))
        //并集
        val rdd3 = rdd1.union(rdd2)
        val rdd4 = rdd1 ++ rdd2 //同上
        //差集，重新分区(hash分区)，有shuffle
        val rdd5 = rdd1.subtract(rdd2)
        //交集
        val rdd6 = rdd1.intersection(rdd2)
        //笛卡尔积，结果是两配对的元组长度为两个集合长度乘积
        val rdd7 = rdd1.cartesian(rdd2)
        //拉链，只支持分区数相同且每个分区内有同样的元素个数，按照集合索引一一对应组成元组
        val rdd8 = rdd1.zip(rdd2)
        //和集合的高级算子zipWithIndex()相同，和自己的索引zip
        val rdd9 = rdd1.zipWithIndex()
        //只要求分区数相同
        val rdd10 = rdd1.zipPartitions(rdd2)((it1, it2) => it1.zip(it2))
        val rdd11 = rdd1.zipPartitions(rdd2)((it1, it2) => it1.zipAll(it2, 100, 200))
        sc.stop()
    }
}
