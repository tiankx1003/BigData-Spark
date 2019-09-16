package com.tian.day03.practice

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 统计出每一个省份中每个城市广告被点击次数的 TOP3
 *
 * @author tian
 * @date 2019/9/16 14:55
 * @version 1.0.0
 */
/*
    => RDD[(((province, city), ads), 1)] reduceByKey
    => RDD[(((province, city), ads), count)] map
    => RDD[((province, city), (ads, count))] groupByKey
    => RDD[((province, city), List[(ads, count)])]
 */
object Practice2 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val line = sc.textFile("file://" + ClassLoader.getSystemResource("agent.log"))
        val rdd1 = line.map(line => {
            val words = line.split(" ")
            (((words(1), words(2)), words(4)), 1)
        })
        val rdd2 = rdd1.reduceByKey(_ + _).map({
            case ((position, ads), count) => (position, (ads, count))
        })
        val rdd3 = rdd2.groupByKey()
            .map({
                case (position, listIt) => (position, listIt.toList.sortBy(-_._2).take(3)) //排序，取前三
            }).sortByKey() //按照省份排序
        rdd3.collect.foreach(println)
        sc.stop()
    }
}
