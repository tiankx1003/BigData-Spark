package com.tian.review.app

import com.tian.review.bean.{CategoryCountInfo, UserVisitAction}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/19 11:13
 * @version 1.0.0
 */
object MainApp {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setAppName("Project").setMaster("local[2]")
        val sc: SparkContext = new SparkContext(conf)
        //文件中每一行数据内容
        val lineRDD: RDD[String] = sc.textFile(ClassLoader.getSystemResource("user_visit_action.txt").getPath)
        //通过map得到用户行为RDD
        val userActionRDD: RDD[UserVisitAction] = lineRDD.map(line => {
            val splits: Array[String] = line.split("_")
            UserVisitAction( //返回用户行为封装成的对象
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
                splits(12).toLong
            )
        })
        val categoryTop10Info: List[CategoryCountInfo] = CategoryTop10.categoryTop10(sc, userActionRDD)
        SessionTop10.sessionTop10_1(sc, userActionRDD, categoryTop10Info)
        println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
        SessionTop10.sessionTop10_2(sc, userActionRDD, categoryTop10Info)
        println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
        SessionTop10.sessionTop10_3(sc, userActionRDD, categoryTop10Info)
    }
}
