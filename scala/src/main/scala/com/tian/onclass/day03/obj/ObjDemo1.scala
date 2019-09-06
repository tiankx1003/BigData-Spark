package com.tian.onclass.day03.obj

/**
 * @author tian
 *         2019/9/6 9:11
 */
object ObjDemo1 {
    def main(args: Array[String]): Unit = {
        new User("aa", 20, "f")
//        new User("bb", 30)
    }
}

//通过默认值的方式解决，本质上不是java中的构造器重载，不能从根本上解决
//class User(var name:String, val age:Int, sex:String = "f")

/*
scala支持构造函数的重载
其他的构造函数使用 def this() {} ,不能写返回值和等号
scala中构造函数有主构造函数和辅助构造函数的区别
主构造函数只能有一个，形参自动成为类的属性
辅助构造函数首行必须调用主构造函数,可以有多个
当调用辅助构造时，本质上还是会调用到主构造
辅助构造之间互相调用时，只能后面调用前面的
 */
class User(var name: String, val age: Int, sex: String) {//sex会被编译器优化掉
    var a:Int = 0
    val b:String = ""

    // 对应java类中的构造代码块，在创建对象时执行
    println("Hello")
    println("World")
    // 静态代码块卸载伴生object中

    def this() { // 构造函数的重载
        this("lisi", 20, "f") //这种写法要求必须调用被重载的构造函数

    }

    def this(name: String) {
        this("wangwu", 20, "f")
    }

    def this(a: Int) { // 辅助构造函数的参量不会成为属性，只是函数局部的常量
        this()
    }

    def this(e: Char) {
        this("10") //辅助构造之间互相调用时，只能后面调用前面的
    }
}