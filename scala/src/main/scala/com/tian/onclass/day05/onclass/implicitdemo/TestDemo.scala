package com.tian.onclass.day05.onclass.implicitdemo

/**
 * @author tian
 *         2019/9/9 14:22
 */
object TestDemo {
    def main(args: Array[String]): Unit = {
        implicit def foo(d: Double) = d.toInt

        val a: Int = 10.6 //自动寻找传入Double返回Int并且有implicit修饰的函数

        implicit def getMyInt(a: Int) = new MyInt(a) //隐式方法

        val ago = "ago"
        val later = "later"
        1 days ago
        2 days later

        implicit class MyRichData(a: Int) { //隐式类
            def years(s: String) = {
                if (s == "ago") println(s"$a years ago")
                else if (s == "later") println(s"$a years later")
                else println("err")
            }
        }

        1 years ago
        2 years later

        implicit val num: Int = 10
        foo1(10) //使用隐式值
        foo1(10)() //使用默认值

        //隐式类、隐式函数、隐式值的查找
        //先查找当前作用域，然后查找相关类型的伴生对象
        implicit a:AA = null
        fooD()

    }

    def foo1(a: Int)(implicit b: Int = 100) //隐式参数
    = println(a + b)

    def fooD(a: AA) = println("A....")
}

class MyInt(val a: Int) {
    def days(s: String) = {
        if (s == "ago") println(s"$a days ago")
        else if (s == "later") println(s"$a days later")
        else println("err")
    }
}

class AA

object AA {
    implicit val d: AA = null
}

class BB[CC]

class CC

object CC {

}
