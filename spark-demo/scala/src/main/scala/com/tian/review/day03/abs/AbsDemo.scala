package com.tian.review.day03.abs

/**
 * 抽象类
 *
 * @author tian
 *         2019/9/6 21:25
 */
object AbsDemo
abstract class Human(val name:String){
    val age:Int
    var sex:String
    val a:Int
    def say(a:Int):Long
}

class Person(override val name: String) extends Human(name){
    override val age: Int = 20
    override var sex: String = _
    override val a: Int = 100
    override def say(a: Int): Long = 100L
}