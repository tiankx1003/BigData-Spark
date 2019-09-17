package com.tian.day04.transmitfun

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * spark序列化
 *
 * @author tian
 * @date 2019/9/17 8:57
 * @version 1.0.0
 */
//TODO 每个语句的执行的位置
/*
1. 方法或函数的传递，对象必须序列化
2. 属性值的传递，对象序列化或使用局部变量传值
 */
object TransmitFun {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf()
            .setAppName("Practice")
            .setMaster("local[*]")
            // 替换默认的序列化机制，可省略，源码中已经替换
            .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
            // 注册需要使用 kryo 序列化的自定义类
            .registerKryoClasses(Array(classOf[Searcher]))
        val sc = new SparkContext(conf)
        val rdd =
            sc.parallelize(List("hadoop", "scala", "hello", "hello", "scala", "world", "hello", "scala"), 2)
        val searcher = new Searcher("hello") with Serializable //动态混入，仍旧不能解决问题
        val result = searcher.getMatchedRDD1(rdd)
        searcher.getMatchedRDD2(rdd) //对象没有序列化，query属性没有序列化
        result.collect.foreach(println)
        sc.stop()
    }
}

/*
Serializable接口是标记接口，不需要实现任何方法
java中的序列化过重，spark使用自己的序列化框架kryo
 */
case class Searcher(val query: String) extends Serializable {
    def isMatch(s: String): Boolean = {
        s.contains(query)
    }

    def getMatchedRDD1(rdd: RDD[String]): RDD[String] = {
        rdd.filter(isMatch)
        rdd.filter(s => true) //匿名函数会自动序列化
    }

    def getMatchedRDD2(rdd: RDD[String]): RDD[String] = {
        rdd.filter(_.contains(query)) //query属性没有序列化
        val q = query //传给局部变量
        rdd.filter(_.contains(q)) //对象是否序列和q没有关系
    }
}
