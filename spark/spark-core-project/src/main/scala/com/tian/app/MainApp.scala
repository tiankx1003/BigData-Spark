package com.tian.app

import com.tian.bean.{CategoryCountInfo, UserVisitAction}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/19 15:39
 * @version 1.0.0
 */
object MainApp {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("project").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val lineRDD =
            sc.textFile(ClassLoader.getSystemResource("user_visit_action.txt").getPath)
        val userActionRDD = lineRDD.map {
            line => {
                val splits = line.split("_")
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
            }
        }
        val categoryTop10List: List[CategoryCountInfo] = CategoryTop10.categoryTop10(sc, userActionRDD)
        SessionTop10.sessionTop10(sc, userActionRDD, categoryTop10List)
        println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
        sc.stop()
    }
}
