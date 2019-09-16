package com.tian.day03

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 取每个省份的每个城市的前三
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
        val line = sc.textFile("E:\\Git\\BigData-Spark\\spark\\spark-core\\src\\main\\resources\\agent.log")
        val rdd1 = line.map(line => {
            val words = line.split(" ")
            (((words(1), words(2)), words(4)), 1)
        })
        val rdd2 = rdd1.reduceByKey(_ + _).map({ // RDD[((province, ads), count)]
            case ((position, ads), count) => (position, (ads, count))
        })
        val rdd3 = rdd2.groupByKey() // RDD[(province, Iterable[(ads, count)])]
            .map({
                case (position, listIt) => (position, listIt.toList.sortBy(-_._2).take(3)) //排序，取前三
            }).sortByKey() //按照省份排序
        rdd3.collect.foreach(println)
        sc.stop()
    }
}
