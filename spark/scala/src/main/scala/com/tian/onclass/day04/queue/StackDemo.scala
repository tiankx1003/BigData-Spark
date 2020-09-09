package com.tian.onclass.day04.queue

import scala.collection.mutable

/**
 * @author tian
 *         2019/9/7 11:20
 */
object StackDemo {
    def main(args: Array[String]): Unit = {
        val stack = mutable.Stack(10,320,20)
        stack.push(100) //入栈 栈顶
        println(stack)
        val ele = stack.pop()
        println(ele)
        println(stack)
    }
}
