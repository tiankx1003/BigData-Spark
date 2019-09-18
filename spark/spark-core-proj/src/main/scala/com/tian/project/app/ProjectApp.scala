package com.tian.project.app

import com.tian.project.bean.{CategoryCountInfo, UserVisitAction}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/18 11:34
 * @version 1.0.0
 */
/*
task个数设置为节点的2~3倍
 */
object ProjectApp {
    def main(args: Array[String]): Unit = {
        // 1.sc初始化
        val conf: SparkConf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc: SparkContext = new SparkContext(conf)
        // 2.读取数据
        // TODO: file://" + ClassLoader.getSystemResource("file") 路径报错
        val lineRDD: RDD[String] = sc.textFile(ClassLoader.getSystemResource("user_visit_action.txt").getPath)
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
        val top10 = CategoryTop10App.statCategoryTop10(sc, userVisitActionRDD)

        // 需求2: top10品类中，每个品类的top10活跃session
        CategorySessionTop10.statCategoryTop10Session1(sc: SparkContext, userVisitActionRDD, top10)
        println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
        CategorySessionTop10.statCategoryTop10Session2(sc: SparkContext, userVisitActionRDD, top10)
        println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
        CategorySessionTop10.statCategoryTop10Session3(sc: SparkContext, userVisitActionRDD, top10)
        sc.stop()
    }
}
