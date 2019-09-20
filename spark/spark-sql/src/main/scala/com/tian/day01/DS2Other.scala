package com.tian.day01

import org.apache.spark.sql.SparkSession

/**
 * @author tian
 * @date 2019/9/20 15:37
 * @version 1.0.0
 */
object DS2Other {
    def main(args: Array[String]): Unit = {
        val spark = SparkSession
            .builder()
            .master("local[*]")
            .appName("DS2Other")
            .getOrCreate()
        import spark.implicits._
        val rdd1 = //样例类RDD
            spark.sparkContext.parallelize(Array(User("Tom", 22, "male"), User("Jack", 21, "female")))
        //rdd -> ds
        val ds = rdd1.toDS
        //ds -> rdd
        val rdd2 = ds.rdd //样例类RDD
        val df = rdd1.toDF
        //df -> ds
        val ds2 = df.as[User]
        //ds -> df
        val df2 = ds2.toDF
        spark.close()
        // TODO: RDD DF DS 三者之间的关系
        /*
        RDD(Spark1.0) 为了支持SQL -> DF(Spark1.3) 因为弱类型 -> DS(Spark1.6)
        都是Spark平台下的分布式弹性数据集
        懒执行
        自动缓存运算
        都有partition
        共同的函数map filter sort...
        import spark.implicits._
        DF和DS都能通过模式匹配获取字段的类型

        RDD一般和spark mlib同时使用
        RDD不支持sparksql操作
        DF存放的是Row
        DF和DS一般不与spark mlib同时使用
        DF和DS支持SparkSql
        DS和DF有方便的保存方式(csv等)
            ...
         */
    }
}
