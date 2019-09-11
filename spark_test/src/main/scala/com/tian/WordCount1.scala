package com.tian

import org.apache.spark.{SparkConf, SparkContext}

/**
 * WordCount1
 * com.tian.WordCount1
 *
 * @author tian
 *         2019/9/11 16:04
 */
object WordCount1 {
    def main(args: Array[String]): Unit = {
        //1.初始化spark context
        val conf = new SparkConf()
            .setAppName("WordCount")
            .setMaster("local[*]") //上传jar包在服务器运行不能设置
        val sc = new SparkContext(conf)

        //2.转换
        val rdd = sc.textFile(args(0)) //手动传入路径
            .flatMap(_.split("\\W+"))
            .map((_, 1))
            .reduceByKey(_ + _)

        //3.行动
        val result = rdd.collect()
        result.foreach(println)

        //4.关闭上下文
        sc.stop()
    }
}
