package com.tian.day03.partitioner

import org.apache.spark.{Partitioner, SparkConf, SparkContext}


/**
 * 自定义分区器
 *
 * @author tian
 * @date 2019/9/16 9:22
 * @version 1.0.0
 */
object MyPartitioner {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 = sc.parallelize(Array(20, 31, 40, 51, 20, 50))
        val rdd2 = rdd1.map((_, null)) //先转为kv形式，partitionBy只能用于kv形式的RDD，
        val rdd3 = rdd2.partitionBy(new MyPartitioner(2)).map(_._1)
        rdd3.glom().collect.foreach(x => println(x.mkString(",")))
        sc.stop()
    }
}

/**
 * 自定义分区类
 * 按照奇偶分区
 *
 * @param partitionNum
 */
class MyPartitioner(var partitionNum: Int) extends Partitioner {
    override def numPartitions: Int = partitionNum

    /**
     * 按照奇偶分区
     *
     * @param key 传入的kv类型RDD的key值，作为分区依据
     * @return
     */
    override def getPartition(key: Any): Int = {
        val k = key.asInstanceOf[Int]
        (k % 2).abs
        /*key match{ //使用模式匹配的方式处理
            case key:Int =>
            case _ =>
        }*/
    }

    override def hashCode(): Int = super.hashCode()

    override def equals(obj: Any): Boolean = super.equals(obj)
}
