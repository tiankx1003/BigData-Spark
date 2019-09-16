package com.tian.day03.actition

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/16 15:31
 * @version 1.0.0
 */
object Foreach {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array("hello", "hello", "world", "scala"))
        //scala.collection.IndexedSeqOptimized.foreach
        /*
        两个foreach不是同一个函数
        RDD.foreach可以把数据写入到外部存储(jdbc,hive,hdfs,hbase...)，每次写入都会创建一个连接，所以很少使用
        map操作也可以往外部写入，但是不安全，每次行动算子都会触发它的执行
        所以map不要和外界有任何数据的写入写出
         */
        rdd1.collect.foreach(println) //在driver输出
        //org.apache.spark.rdd.RDD.foreach
        rdd1.foreach(println) //在executor输出,local模式可以显示输出，如果是集群模式则无法显示输出
        /*rdd1.foreachPartition(it => { //每个分区连接一次
            it.foreach(???)
            it.toList //会把数据一次全部加载到内存导致OOM，缺点
        })*/
        sc.stop()
    }
}
