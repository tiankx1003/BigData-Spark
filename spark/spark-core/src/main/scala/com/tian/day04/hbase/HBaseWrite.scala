package com.tian.day04.hbase

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapreduce.Job
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/17 20:54
 * @version 1.0.0
 */
object HBaseWrite {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc: SparkContext = new SparkContext(conf)
        val hbaseConf: Configuration = HBaseConfiguration.create()
        hbaseConf.set("hbase.zookeeper.quorum", "hadoop102,hadoop103,hadoop104")
        hbaseConf.set(TableOutputFormat.OUTPUT_TABLE, "student")
        //通过job来设置输出的格式的类
        val job: Job = Job.getInstance(hbaseConf)
        job.setOutputFormatClass(classOf[TableOutputFormat[ImmutableBytesWritable]])
        job.setOutputKeyClass(classOf[ImmutableBytesWritable])
        job.setOutputValueClass(classOf[Put])

        val initialRDD: RDD[(String, String, String)] =
            sc.parallelize(List(("10", "apple", "11"), ("20", "banana", "21")))
        val hbaseRDD: RDD[(ImmutableBytesWritable, Put)] = initialRDD.map {
            case (row, name, weight) =>
                //封装rowKey
                val rowKey: ImmutableBytesWritable = new ImmutableBytesWritable()
                rowKey.set(Bytes.toBytes(row))
                val put: Put = new Put(Bytes.toBytes(row))
                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes(name))
                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("weight"), Bytes.toBytes(weight))
                (rowKey, put)
        }
        hbaseRDD.saveAsNewAPIHadoopDataset(job.getConfiguration)
        sc.stop()
    }
}
