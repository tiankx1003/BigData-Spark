package com.tian.day01.dataresource

import java.io.{BufferedReader, InputStreamReader}
import java.net.Socket

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * 自定义数据源的本质就是自定义接收器
 *
 * @author tian
 * @date 2019/9/23 20:09
 * @version 1.0.0
 */
object CustomDStream {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("CustomDStream")
        val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))
        ssc.receiverStream(new MyReceiver("hadoop102", 9999)) //: ReceiverInputDStream[String]
            .flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).print(100)
        ssc.start()
        ssc.awaitTermination()
    }
}

/**
 * 自定义接收器实现Socket数据源
 * 继承传参设置存储级别
 *
 * @param host socket host
 * @param port socket 端口
 */
class MyReceiver(val host: String, val port: Int) extends Receiver[String](StorageLevel.MEMORY_ONLY) {
    /**
     * 接收器启动时调用的方法
     * 启动子线程，不断的循环接收数据
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
     * 封装用于接收数据的方法
     * 在启动方法子线程中调用
     */
    def receiveData(): Unit = {
        try {
            val socket: Socket = new Socket(host, port)
            //字节流转字符流后套缓冲流
            val reader: BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream, "utf-8"))
            var line: String = reader.readLine() //读取一行
            while (line != null) {
                store(line)
                line = reader.readLine()
            }
            reader.close()
            socket.close()
        } catch { //在netcat刚开始没数据时排除异常
            case e: Exception => e.printStackTrace()
        } finally { //无论有没有数据是否抛出异常，任务都要重启
            restart("restarting...")
        }
    }
}