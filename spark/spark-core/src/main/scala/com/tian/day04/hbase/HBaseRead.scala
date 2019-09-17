package com.tian.day04.hbase

import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration}
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/17 20:54
 * @version 1.0.0
 */
// TODO 建表验证
object HBaseRead {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val hbaseConf = HBaseConfiguration.create()
        hbaseConf.set("hbase.zookeeper.quorum", "hadoop102,hadoop103,hadoop104")
        hbaseConf.set(TableInputFormat.INPUT_TABLE, "student")
        val rdd1 = sc.newAPIHadoopRDD(
            hbaseConf,
            classOf[TableInputFormat],
            classOf[ImmutableBytesWritable],
            classOf[Result]
        )
        val rdd2 = rdd1.map {
            case (key, result) => {
                val cells = result.listCells()
                import scala.collection.JavaConversions._
                for (cell <- cells) {
                    println(Bytes.toString(CellUtil.cloneQualifier(cell)))
                }
            }
        }
        rdd2.collect.foreach(println)
        sc.stop()
    }
}
