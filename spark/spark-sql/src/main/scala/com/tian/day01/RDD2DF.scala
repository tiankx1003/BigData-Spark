package com.tian.day01

import org.apache.spark.sql.SparkSession

/**
 * @author tian
 * @date 2019/9/20 14:19
 * @version 1.0.0
 */
object RDD2DF {
    def main(args: Array[String]): Unit = {
        //先初始化SparkSession
        val spark = SparkSession.builder()
            .master("local[2]")
            .appName("RDD2DF")
            .getOrCreate()
        //导入隐式转换，spark-shell中自动导入隐式转换
        import spark.implicits._
        //创建rdd
        val rdd1 =
            spark.sparkContext.parallelize(Array(("Tom", 22, "male"), ("Jack", 21, "female")))
        val rdd2 = //样例类RDD
            spark.sparkContext.parallelize(Array(User("Tom", 22, "male"), User("Jack", 21, "female")))
        rdd1.toDF("name", "age", "sex").show
        rdd2.toDF.show //样例类会自动添加字段名
        spark.close()
    }
}

case class User(name: String, age: Int, sex: String)