package com.tian.day04.readwrite

import org.apache.spark.{SparkConf, SparkContext}

/**
 * TextFile文件读写
 *
 * @author tian
 * @date 2019/9/17 18:15
 * @version 1.0.0
 */
object ReadWrite {
    def main(args: Array[String]): Unit = {
        System.setProperty("HADOOP_USER_NAME", "tian") //设置Hadoop用户名
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        //读取TextFile
        val rdd1 = sc.textFile("file://" + ClassLoader.getSystemResource("read.txt"))
        //写入TextFile到HDFS
        rdd1.saveAsTextFile("hdfs://hadoop102:9000/SparkFile/read.txt")
    }
}
