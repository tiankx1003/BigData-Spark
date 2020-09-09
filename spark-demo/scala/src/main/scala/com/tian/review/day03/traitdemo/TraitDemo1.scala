package com.tian.review.day03.traitdemo

/**
 * @author tian
 *         2019/9/9 18:47
 */
object TraitDemo1 {
    def main(args: Array[String]): Unit = {
        val usb = new HuaWeiUsb
        usb.print()
    }
}

class HuaWeiUsb extends Usb with Console { //特质混入
    //重写抽象方法
    override def insert(): Unit = println("huawei usb insert...")
    override def work: Unit = println("huawei usb work...")
    override def discard(): Unit = println("huawei usb discard...")
    override def display: Unit = println("huawei usb display...")
    override def print(): Unit = { //调用方法
        insert()
        work
        discard()
        display
    }
}

trait Test0 {
    val a = 10
    var b = 20
    var c: Int //抽象
    def foo = {} //默认方法
    def foo1: Int //抽象方法
}

trait Usb {
    def insert()
    def work
    def discard():Unit
    def display:Unit
}

trait Console {
    def print(): Unit
}