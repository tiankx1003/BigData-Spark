package com.tian.day04.hbase

import java.util

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.{Cell, CellUtil, HBaseConfiguration}
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/17 20:54
 * @version 1.0.0
 */
// TODO 建表验证
object HBaseRead {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc: SparkContext = new SparkContext(conf)
        val hbaseConf: Configuration = HBaseConfiguration.create()
        hbaseConf.set("hbase.zookeeper.quorum", "hadoop102,hadoop103,hadoop104")
        hbaseConf.set(TableInputFormat.INPUT_TABLE, "stu")
        val rdd1: RDD[(ImmutableBytesWritable, Result)] = sc.newAPIHadoopRDD(
            hbaseConf,
            classOf[TableInputFormat],
            classOf[ImmutableBytesWritable],
            classOf[Result]
        )
        val rdd2: RDD[Unit] = rdd1.map {
            case (_, result) =>
                val cells: util.List[Cell] = result.listCells()
                import scala.collection.JavaConversions._
                for (cell <- cells) {
                    println(Bytes.toString(CellUtil.cloneQualifier(cell)))
                }
        }
        rdd2.collect.foreach(println)
        sc.stop()
    }
}
