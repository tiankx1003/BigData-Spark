package com.tian.onclass.day04.queue

import scala.collection.mutable

/**
 * 队列
 *
 * @author tian
 *         2019/9/7 11:17
 */
object QueueDemo {
    def main(args: Array[String]): Unit = {
        val qu = mutable.Queue(10, 20, 30)
        qu.enqueue(100) //入队
        val ele = qu.dequeue() //出队,返回出队的元素
    }
}
