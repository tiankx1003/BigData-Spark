package com.tian.onclass.day05.onclass.pattern

/**
 * @author tian
 *         2019/9/9 16:51
 */
object PatternObj3 {
    def main(args: Array[String]): Unit = {
        val user = new User("lisi", 20)
        val user2 = false
        user match {
            case User(name, age) => println(name)
        }
        /*
        user2 match {
            case User(name, age) => println(name) //MatchError
        }
        */
        val User(name, _) = user
        println(name)
    }
}

/*

object User {
    def unapply(arg: User): Option[(String, Int)] = Some(arg.name, arg.age)
}

class User(val name: String, val age: Int)
*/

/**
 * 样例类
 *
 * @param name
 * @param age
 */
case class User(val name: String, val age: Int) //等效于伴生类和伴生对象的声明以及unapply方法

