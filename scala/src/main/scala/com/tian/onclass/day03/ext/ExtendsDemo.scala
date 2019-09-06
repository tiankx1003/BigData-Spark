package com.tian.onclass.day03.ext

/**
 * @author tian
 *         2019/9/6 10:25
 */
/*
面向对象三大特征
1. 封装

2. 继承
    scala也是单继承


    方法覆写规则，两同(方法名，形参列表)，两小(返回值类型，权限修饰符)，一大(权限修饰符)

    方法的覆写，必须添加override关键字
    属性的覆写，val可以覆写val和没有参数的def
                var只能覆写抽象的var

    继承时构造器的调用
    子类的主构造才有权利调用父类的构造器

3. 多态
    编译时类型

    运行时类型

    编译时类型和运行时类型不一致就是多态
    1. 编译能否通过看编译时类型
    2. 执行的具体表现看运行时类型

    * java中属性没有多态，但是scala的属性有多态
    * 在scala中调用属性本质上是调用方法，方法有多态

 */

object ExtendsDemo {
    def main(args: Array[String]): Unit = {
        val stu = new Stu
        stu.say()
        val stu2:Person = new Stu // 多态
        stu2.say()
//        stu2.eat()

    }
}


class Person{
    def say() = {
        println("person say...")
    }
}

class Stu extends Person{
    override def say()={
        println("stu say...")
    }
    def eat()={
        println("stu eat...")
    }
}



