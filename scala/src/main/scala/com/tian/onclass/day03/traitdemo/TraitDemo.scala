package com.tian.onclass.day03.`trait`

/**
 * @author tian
 *         2019/9/6 15:30
 */
/*
java1.8之前接口中还有常量和抽象方法，1.8之后添加了默认方法
scala中没有接口，使用trait(特质)在实现
 */
object TraitDemo {
    def main(args: Array[String]): Unit = {
        val usb: Console = new HuaweiUsb
        usb.print()
    }
}

trait Usb0 {
    val a: Int = 10
    var b: Int = 20 //
    var c: Int //抽象
    def foo = {} //默认方法
    def foo2: Int //抽象方法
}

trait Usb {
    def insert()

    def work

    def discard(): Unit

    def display: Unit
}

trait Console {
    def print(): Unit
}

class HuaweiUsb extends Usb with Console { //混入特质
    override def insert(): Unit = println("HuaWei USB insert...")

    override def work(): Unit = println("HuaWei USB work...")

    override def discard(): Unit = println("HuaWei USB discard...")

    override def display: Unit = println("HuaWei USB Display...")

    override def print(): Unit = {
        insert()
        work()
        discard()
        display
    }
}


