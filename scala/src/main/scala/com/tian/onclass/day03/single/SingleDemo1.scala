package com.tian.onclass.day03.single

/**
 * @author tian
 *         2019/9/6 14:15
 */
// TODO 伴生对象视频
/*
java中不够面向对象的元素:基本类型、静态
scala中通过对象object来实现静态的效果，同时解决了单例问题
 */
object SingleDemo1 {
    def main(args: Array[String]): Unit = {
        val dog1 = Dog //?
        val dog2 = Dog()
        dog1.speak()
        dog2.speak()
    }
}

sealed abstract class Father //sealed 关键字限制只能在该文件中。。。
object A extends Father  //A为单例对象
object B //已经是单例对象，同时又叫独立对象

/*
伴生类和伴生对象必须位于同一个文件中
 */
object Dog extends Dog{//Dog这个类的伴生对象，实现了java中静态的内容
    private def foo() ={
        val dog = new Dog
        dog.speak()
        val dog1 = Dog //?
        val dog2 = Dog()
    }
    speak() // 继承自己的伴生类后可直接调用
    def apply() = new Dog()
}
class Dog {//Dog这个对象的伴生类，实现了java中非静态的内容
    Dog.foo() //可以互相访问对方的私有成员
    def speak() = {}
}

object Cat{
    def apply(color: String): Cat = new Cat(color)
//    def apply(): Cat = new Cat()
}

class Cat(val color:String){
    def speak() = println("cat speak")
}
