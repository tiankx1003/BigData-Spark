package com.tian.review.day03.ext

/**
 * 继承
 *
 * @author tian
 *         2019/9/6 20:42
 */
object ExtendsDemo1 {
    def main(args: Array[String]): Unit = {

    }
}

class Person {
    def say = println("person say...")
}

class Stu extends Person {
    override def say: Unit = println("stu say...")

    def eat = println("stu eat...")
}