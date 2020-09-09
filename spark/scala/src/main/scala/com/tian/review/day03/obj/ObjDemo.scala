package com.tian.review.day03.obj

/**
 * 构造器
 *
 * @author tian
 *         2019/9/6 19:18
 */
object ObjDemo {
    def main(args: Array[String]): Unit = {

    }
}

//没有使用val或var修饰，编译器会优化掉sex
class User0(var name: String, val age: Int, sex: String = "f")

class User1(var name: String, val age: Int, sex: String) {
    var a: Int = 0
    val b: String = ""

    //对应java中累的构造代码块，在创建对象时执行
    println("Hello")
    println("World")

    //静态代码块写在伴生object中
    def this() { //构造函数的重载
        this("lisi", 20, "f") //必须调用被重载的构造函数
        println("this")
    }

    def this(name: String) {//辅构造的参量不会成为属性，知识函数局部的常量
        this("wangwu", 20, "m")
        println("wangwu")
    }

    def this(e: Char) {
        this("10") //辅构造相互调用时只能后面调用前面的
    }
}
