package com.tian.review.day04.queue

import scala.collection.mutable

/**
 * 队列
 * FIFO
 *
 * @author tian
 *         2019/9/11 20:48
 */
object QueueDemo {
    def main(args: Array[String]): Unit = {
        val queue = mutable.Queue(10, 20, 30)
        queue.enqueue(100) //入队
        val ele = queue.dequeue() //出队，返回出队的元素
    }
}
