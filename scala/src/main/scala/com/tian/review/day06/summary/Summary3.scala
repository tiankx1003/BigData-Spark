package com.tian.review.day06.summary

/**
 * apply & update
 *
 * @author tian
 *         2019/9/10 21:19
 */
object Summary3 {
    def main(args: Array[String]): Unit = {
        //TODO 当前进度
        val user1 = new User("lisi",20)
        User() //调用伴生对象的apply方法
        user1("name") //调用伴生类中的抽象方法
        user1("lisi") = "wangwu" //调用伴生类中的apply方法
        !user1 //一元运算符
        (user1.fun _).apply
    }
}

class User(var name: String, var age: Int){
    def apply(propName: String) = println(propName)
    def update(propName: String, v: String) =
        if(propName == "name") this.name = v
    def unary_! = println("Hello")
    def fun() = println("fun...")
}

object User{
    def apply() = println("object apply...")
}
