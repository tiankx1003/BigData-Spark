package com.tian.onclass.day03.traitdemo

/**
 * 执行顺序
 * trait也有自己的构造器
 * @author tian
 *         2019/9/6 15:42
 */
object TraitDemo2 {
    def main(args: Array[String]): Unit = {
        new ABC //按照混入顺序执行
    }
}

trait F{
    println("f")
    def f = println("f..")
}
trait A extends F{
    println("a")
    override def f = println("a..")
}

trait B extends F{
    println("b")
    override def f = println("b..")
}

trait C extends F{
    println("c")
    override def f = {
        super.f //会本混入顺序决定
        super[F].f //直接指明
        println("c..")
    }
}

class ABC extends A with B with C with F {
    println("abc")
//    override def f = println("abc..")
}

// TODO 接口冲突问题，菱形继承关系