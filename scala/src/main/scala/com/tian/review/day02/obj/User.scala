package com.tian.review.day02.obj

import scala.beans.BeanProperty

/**
 * bean类
 *
 * @author tian
 *         2019/9/5 10:36
 */
class User(var name: String, val age: Int, gender: Char,@BeanProperty addr:String) {
    /*
    从函数式编程的角度来看，是声明了函数了参量
    从面向对象的角度来看，上面的语句同时声明了类，和属性(公有私有，getter，setter)，构造器
    当使用var时表示公有属性，提供name方法作用同getter，name_$eq方法作用同setter
    当使用val时表示私有属性，不提供name_$eq只提供name方法供调用
    当没有具体指明val或var，则表示这是私有属性，没有公共的name,name_$eq方法完成set和get
    当使用@BeanProperty注解修饰时，scala会按照val或var约束额外提供复合JavaBean标准的setter和getter
     */
    def foo() ={
        println(name) //闭包，从函数式编程的角度，调用外部变量
        println(this.name) //使用this表示从面向对象的角度，调用属性
    }
    def foo2() = {
        val name = "aaa"
        println(name) //调用的是内部定义的name
        println(this.name) //调用的是属性值(外部变量)name，闭包
    }
}

class Person(var name: String) //如果一个类中没有内容，可以省略大括号
