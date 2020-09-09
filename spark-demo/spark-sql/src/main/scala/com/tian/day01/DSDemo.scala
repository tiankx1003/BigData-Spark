package com.tian.day01

import org.apache.spark.sql.SparkSession

/**
 * DataSet
 *
 * @author tian
 * @date 2019/9/20 15:21
 * @version 1.0.0
 */
/*
DS是spark中真正的数据集
当DataSet中存放Row时，数据集为DF
即DF是DS的一个特例
DS 强类型，记录存放数据类型
DF row为弱类型 row.getInt()，row.getString() 在不知道类型时使用，不安全
 */
object DSDemo {
    def main(args: Array[String]): Unit = {
        val spark = SparkSession
            .builder()
            .master("local[*]")
            .appName("DSName")
            .getOrCreate()
        import spark.implicits._
        val arr1 = Seq(User("lisi", 20, "male"), User("wangwu", 21, "female)"))
        val ds = arr1.toDS()
        ds.show
        ds.select("name").show
        ds.map(user => user.name) //ds中存放的是样例类，强类型
        /*ds.createOrReplaceTempView("user")
        val sql = spark.sql("select * from user")*/
        //强类型
        ds.filter(_.age > 19).show
        //弱类型
        ds.filter($"age" > 19).show

        spark.close()
    }
}
