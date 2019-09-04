package com.tian.review.day02.onclass.obj

/**
 * @author tian
 *         2019/9/4 16:35
 */
object ObjDemo1 {
    val user = new User("lisi", 20,"f")
    println(user)
//    println(user.name)//实际是调用隐藏的name方法，
    println(user.age)
//    user.name = "wangwu" //实际是调用了name_$eq(String)方法
//    user.age = 22
}
