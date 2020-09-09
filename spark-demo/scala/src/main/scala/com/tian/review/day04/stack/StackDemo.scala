package com.tian.review.day04.stack

import scala.collection.mutable

/**
 * 栈
 * FILO
 *
 * @author tian
 *         2019/9/11 20:54
 */
object StackDemo {
    def main(args: Array[String]): Unit = {
        val stack = mutable.Stack(10,20,30)
        stack.push(100) //入栈
        val ele = stack.pop() //出栈，返回出栈的元素
    }
}
