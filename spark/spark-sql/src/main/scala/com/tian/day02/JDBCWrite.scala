package com.tian.day02

import java.util.Properties

import com.tian.day01.User
import org.apache.spark.sql.SparkSession

/**
 * @author tian
 * @date 2019/9/21 9:17
 * @version 1.0.0
 */
object JDBCWrite {
    def main(args: Array[String]): Unit = {
        val spark = SparkSession
            .builder()
            .master("local[*]")
            .appName("JDBCWrite")
            .getOrCreate()
        import spark.implicits._
        val arr1 = spark.sparkContext.parallelize(Seq(User("wangwu", 19, "female"), User("lisi", 20, "male")))
        val df = arr1.toDF
        //通用写
        df.write
            .format("jdbc")
            .option("url", "jdbc:mysql://hadoop102:3306/rdd")
            .option("user", "root")
            .option("password", "root")
            .option("dbtable", "person1")
            .mode("append")
            .save()
        //专用写
        val props = new Properties()
        props.setProperty("user","root")
        props.setProperty("password","root")
        df.write.jdbc("jdbc:mysql://hadoop102:3306/rdd","person2",props)
        spark.close()
    }
}
