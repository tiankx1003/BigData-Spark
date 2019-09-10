package com.tian.onclass.day06.summary

/**
 * @author tian
 *         2019/9/10 14:37
 */
/*
apply:
    1.在伴生对象中，伴生对象(...) 其实就是在调用伴生对象的apply方法
    2.在伴生类中
    3.用apply调用方法
 */
object TypeDemo4 {
    def main(args: Array[String]): Unit = {
        val user = new User("lisi", 20)
        user("name") //调用伴生类中的apply方法
        User() //调用伴生对象的apply方法
        //        (user.f0_).apply
        user("name") = "wangwu" //调用update
        !user //一元运算符
    }
}

object User {
    def apply() = println("伴生对象 apply")
}

class User(var name: String, var age: Int) {
    //用于调用对象的属性过
    def apply(prop: String) = println(prop)
    def f0() = println("foo...")
    def update(propName: String, v: String) =
        if (propName == "name") this.name = v
    def unary_! = println("...") //一元前置运算符
}
