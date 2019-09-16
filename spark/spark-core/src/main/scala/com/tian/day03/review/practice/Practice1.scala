package com.tian.day03.review.practice

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/16 19:40
 * @version 1.0.0
 */
/*
数据结构：时间戳，省份，城市，用户，广告，字段使用空格分割
1516609143867 6 7 64 16
1516609143869 9 4 75 18
1516609143869 1 7 87 12
统计出每一个省份广告被点击次数的 TOP3
RDD[((province,ads),1)] reduceByKey
RDD[((province,ads),count)] map
RDD[(province,(ads,count))] groupByKey
RDD[(province,Iterable[List])] map
RDD[(province,List[(ads,count)])]
 */
object Practice1 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val line = sc.textFile("file://" + ClassLoader.getSystemResource("agent.log"))
        val result = line.map(line => {
            val words = line.split(" ")
            ((words(1), words(4)), 1)
        }).reduceByKey(_ + _).map({
            case ((province, ads), count) => (province, (ads, count))
        }).groupByKey().map({
            case (province, countIt) => (province, countIt.toList.sortBy(_._2)(Ordering.Int.reverse).take(3))
        }).sortByKey()
        result.collect.foreach(println)
        sc.stop()
    }
}
