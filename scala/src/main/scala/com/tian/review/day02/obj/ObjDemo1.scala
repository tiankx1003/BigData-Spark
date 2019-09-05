package com.tian.review.day02.obj

/**
 * @author tian
 *         2019/9/5 10:37
 */
object ObjDemo1 {
    def main(args: Array[String]): Unit = {
        // TODO 声明user为val，属性可变更，val的意义在于内存位置不变，即不可被赋值为其他对象
        val user = new User("wangwu", 20, 'f', "Shenzhen")
        println(user.name) // 实际调用的是name方法
        user.name = "lisi" //实际调用的是name_$eq方法
        println(user.name)
        val user2 = new User("zhangsan", 30, 'm', "Guangzhou")
        //        user = user2
    }
}
