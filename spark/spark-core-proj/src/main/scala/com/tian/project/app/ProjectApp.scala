package com.tian.project.app

import com.tian.project.bean.UserVisitAction
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/18 11:34
 * @version 1.0.0
 */
object ProjectApp {
    def main(args: Array[String]): Unit = {
        // 1.sc初始化
        val conf: SparkConf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc: SparkContext = new SparkContext(conf)
        // 2.读取数据
        //val lineRDD: RDD[String] = sc.textFile("file://" + ClassLoader.getSystemResource("user_visit_action.txt"))
        val lineRDD = sc.textFile("spark-core-proj/src/main/resources/user_visit_action.txt")
        //lineRDD.collect.foreach(println) //输出验证
        //3 .封装到样例类
        val userVisitActionRDD: RDD[UserVisitAction] = lineRDD.map(line => {
            val splits: Array[String] = line.split("_")
            UserVisitAction(
                splits(0),
                splits(1).toLong,
                splits(2),
                splits(3).toLong,
                splits(4),
                splits(5),
                splits(6).toLong,
                splits(7).toLong,
                splits(8),
                splits(9),
                splits(10),
                splits(11),
                splits(12).toLong)
        })
        // userVisitActionRDD.collect.foreach(println) //输出验证

        // 需求1: top10的热门品类
        CategoryTop10App.statCategoryTop10(sc, userVisitActionRDD)


        sc.stop()
    }
}
