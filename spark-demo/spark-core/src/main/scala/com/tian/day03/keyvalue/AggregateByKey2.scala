package com.tian.day03.keyvalue

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/16 10:28
 * @version 1.0.0
 */
object AggregateByKey2 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(List(("a", 2), ("a", 2), ("c", 4), ("b", 3), ("c", 6), ("c", 8)), 2)
        //宽依赖，
        //分区内和分区间聚合逻辑不同
        rdd1.aggregateByKey(Int.MinValue)(math.max(_, _), _ + _) //部分应用函数
        rdd1.aggregateByKey(Int.MinValue)((x, y) => x.max(y), _ + _) //返回每个key对应value的最大值
        val rdd2 = rdd1.aggregateByKey(Int.MinValue)(math.max, _ + _)
        rdd2.collect.foreach(println)
        sc.stop()
    }
}
