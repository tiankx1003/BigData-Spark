package com.tian.onclass.day05.pattern

/**
 * @author tian
 *         2019/9/9 15:19
 */
/*
泛型擦除
在编译结束后，所有关于泛型的信息都会被擦除
 */
object PatternType2 {
    def main(args: Array[String]): Unit = {
        foo(Array(1, 20))
        foo(Map("a" -> 52))
        foo(Map("b" -> 52.2)) //泛型擦除
    }

    def foo(obj: Any) = {
        obj match {
            case a: Array[Int] => println(a.mkString(", ")) //Array[Int]中的Int，数组本质上还是java中的数组,int[]
            //case a: Map[String, Int] => "Map[String, Int]..." //写泛型没有意义
            case a: Map[_, _] => println("Map[_,_]...")
            case a: List[_] => println("List[_]...")
            case _: Int =>
            case _ =>

        }
    }
}
