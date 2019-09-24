package com.tian.day01.review.windowopt

import org.apache.spark.sql.SparkSession

/**
 * @author tian
 * @date 2019/9/24 18:40
 * @version 1.0.0
 */
object WordCountWindow {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
            .builder()
            .master("local[2]")
            .appName("WordCountWindow")
            .getOrCreate()

    }
}
