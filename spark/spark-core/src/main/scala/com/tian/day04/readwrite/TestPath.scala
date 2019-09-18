package com.tian.day04.readwrite

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
 * @author tian
 * @date 2019/9/18 18:44
 * @version 1.0.0
 */
object TestPath {
    def main(args: Array[String]): Unit = {
        // 1. 初始化spark配置信息, 并建立到spark的连接
        val conf = new SparkConf().setAppName("Practice").setMaster("local[*]")
        val sc = new SparkContext(conf)
        // 2. 从文件中读取数据, 得到 RDD. RDD中存储的是文件的中的每行数据
        val lines: RDD[String] = sc.textFile("file://" + ClassLoader.getSystemResource("agent.log"))

        // 3. ((provice, ad), 1)
        val provinceADAndOne: RDD[((String, String), Int)] = lines.map(line => {
            val splits: Array[String] = line.split(" ")
            ((splits(1), splits(4)), 1)
        })
        // 4. 计算每个省份每个广告被点击的总次数
        val provinceADSum: RDD[((String, String), Int)] = provinceADAndOne.reduceByKey(_ + _)

        // 5. 将省份作为key，广告加点击数为value： (Province,(AD,sum))
        val provinceToAdSum: RDD[(String, (String, Int))] = provinceADSum.map(x => (x._1._1, (x._1._2, x._2)))

        // 6. 按照省份进行分组
        val provinceGroup: RDD[(String, Iterable[(String, Int)])] = provinceToAdSum.groupByKey()

        //7. 对同一个省份的广告进行排序, 按照点击数的降序
        val result: RDD[(String, List[(String, Int)])] = provinceGroup.mapValues {
            x => x.toList.sortBy(_._2)(Ordering.Int.reverse).take(3)
        }

        //8. 按照省份的升序展示最终结果
        result.sortBy(_._1).collect.foreach(println)

        // 9. 关闭连接
        sc.stop()



    }
}
/**
 * Read a text file from HDFS, a local file system (available on all nodes), or any
 * Hadoop-supported file system URI, and return it as an RDD of Strings.
 */
