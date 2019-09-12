package com.tian.day02

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 创建一个RDD
 *
 * @author tian
 *         2019/9/12 9:31
 */
/*
得到RDD的3种途径
1.通过标准的scala集合来得到
2.从外部存储得到
3.从其他RDD转换得到
 */
object CreateRDD {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("CreateRDD").setMaster("local[2]")
        val sc = new SparkContext(conf)
        //1. 通过标准的scala集合得到RDD
        val list1 = List(20, 30, 40, 40, 50)
        val rdd = sc.parallelize(list1)
        val list2 = sc.makeRDD(list1) //效果同上，对有些集合会有优化
        rdd.collect().foreach(println)
        //2. 通过外部存储得到RDD
        val linesRDD = sc.textFile("e:/test.txt") //存放一行数据
        //3. 从其他RDD转换得到
        sc.stop()
    }
}
