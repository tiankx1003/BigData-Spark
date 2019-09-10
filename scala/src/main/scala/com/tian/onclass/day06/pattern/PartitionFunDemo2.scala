package com.tian.onclass.day06.pattern

/**
 * @author tian
 *         2019/9/10 9:15
 */
object PartitionFunDemo2 {
    def main(args: Array[String]): Unit = {
        val list1 = List(1, 2, 4, "abc", false)

        val list2 = list1.map { //map把偏函数当作普通的函数对待了
            case a: Int => a * a
            case _ =>
        }
        println(list2) // 1,4,16,(),()

        //与spark中的collect是两种东西
        val list3 = list1.collect { //scala中只有collect支持偏函数
            case a: Int => a * a
        }
        println(list3)

        //使用map处理元组时更方便
        val map1 = Map(1 -> (2, 3), 10 -> (20, 30), 100 -> (200, 300))
        val map2 = map1.map {
            case (k, (_, v)) => (k + 1, v + 1)
        }
        println(map2)
        //偏函数可看作是对模式匹配的进一步封装

        def f0: PartialFunction[Int, Int] = {
            { //返回的是偏函数
                case a => 19
            }
        }

        def f00: PartialFunction[Int, Int] = { //同上
            case a => 10
        }

        f0
        //        f00

        def f1(a: Int) = {
            println(a)
        }

        //函数传参_后调用apply函数
        //        f1 _.apply(10) //语法错误
        (f1 _).apply(10)
        val f = f1 _
        f.apply(100)
    }
}
