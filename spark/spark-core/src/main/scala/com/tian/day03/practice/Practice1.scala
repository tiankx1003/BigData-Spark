package com.tian.day03.practice

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/16 14:22
 * @version 1.0.0
 */
/*
1.	数据结构：时间戳，省份，城市，用户，广告，字段使用空格分割。
1516609143867 6 7 64 16
1516609143869 9 4 75 18
1516609143869 1 7 87 12下载数据
2.	需求: 统计出每一个省份广告被点击次数的 TOP3
3.
        ...
    => RDD[((province, ads), 1)] reduceByKey
    => RDD[((province, ads), count)] map
    => RDD[(province, (ads, count))] groupByKey
    => RDD[(province, List[(ads, count)])]
 */
object Practice1 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val line = sc.textFile("file://" + ClassLoader.getSystemResource("agent.log"))
        val rdd1 = line.map(line => {
            val words = line.split(" ")
            // RDD[((province, ads), 1)]
            ((words(1), words(4)), 1)
        })
        val rdd2 = rdd1.reduceByKey(_ + _).map({ // RDD[((province, ads), count)]
            // RDD[(province, (ads, count))]
            case ((province, ads), count) => (province, (ads, count))
        })
        val rdd3 = rdd2.groupByKey() // RDD[(province, Iterable[(ads, count)])]
            .map({ //RDD[(province, List[(ads, count)])]
                case (province, listIt) => (province, listIt.toList.sortBy(-_._2).take(3)) //排序，取前三
            }).sortByKey() //按照省份排序
        rdd3.collect.foreach(println)
        sc.stop()
    }
}
