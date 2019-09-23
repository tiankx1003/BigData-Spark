package com.tian.day01

import java.io.{BufferedReader, InputStreamReader}
import java.net.Socket

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.receiver.Receiver

/**
 * 自定义数据源的本质就是自定义接收器
 *
 * @author tian
 * @date 2019/9/23 10:25
 * @version 1.0.0
 */
object CustomReceiver {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setMaster("local[2]").setAppName("wordcount")
        val ssc = new StreamingContext(conf, Seconds(4)) //传入时间间隔
        ssc.receiverStream(new MyReceiver("hadoop102", 9998))
            .flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).print(100)
        ssc.start()
        ssc.awaitTermination()
    }
}

/**
 * 自定义接收器从socket接受数据
 */
class MyReceiver(val host: String, val port: Int) extends Receiver[String](StorageLevel.MEMORY_ONLY) { //传入存储级别
    /**
     * 接收器启动时调用的方法
     * 启动子线程，循环不断的接收数据
     */
    override def onStart(): Unit = {
        new Thread() {
            override def run(): Unit = receiveData()
        }.start()
    }

    /**
     * 接收器停止时的回调方法
     */
    override def onStop(): Unit = ???

    /**
     * 封装接受数据的方法
     */
    def receiveData() = {
        try { //为了解决前面没数据报异常的问题
            //从socket接受数据
            val socket = new Socket(host, port)
            val reader = new BufferedReader(new InputStreamReader(socket.getInputStream, "utf-8")) //字节流转字符流
            var line = reader.readLine()
            while (line != null) {
                store(line)
                line = reader.readLine()
            }
            reader.close()
            socket.close()
        } catch {
            case e: Exception => e.printStackTrace
        } finally {
            //重启任务
            restart("restart............")
        }
    }
}
