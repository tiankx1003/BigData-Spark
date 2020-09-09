package com.tian.review.day03.single

/**
 * @author tian
 *         2019/9/7 8:54
 */

object SingleDemo {
    def main(args: Array[String]): Unit = {
        val dog = new Dog("red")
        dog.speak()
        val dog1 = Dog //调用了apply()方法
        val dog2 = Dog("white")
    }
}

class Dog(val color: String) {
    def speak() = println("dog speak...")
    Dog.foo() //可以访问对方的私有成员
}

object Dog {
    private def foo() = println("private dog foo...")
    def apply(color: String): Dog = new Dog(color)
}

sealed abstract class Father //sealed修改只能在该文件中继承
object A extends Father //单例对象，通过继承限制类
object B //单例对象，也叫独立对象