package com.tian.review.day06.pattern

/**
 * @author tian
 *         2019/9/10 20:43
 */
object PartialFunDemo2 {
    def main(args: Array[String]): Unit = {
        val list1 = List(1, 2, 3, "aa", false)
        //map传入偏函数会被当作普通函数对待
        val list2 = list1.map {
            case a: Int => a * a
            case _ =>
        }
        println(list2)
        //scala中只有collect支持偏函数
        val list3 = list1.collect {
            case a: Int => a * a
        }
        println(list3)
        //使用map传入偏函处理元组更方便
        val map1 = Map(1 -> (2, 3), 10 -> (20, 30), 100 -> (200, 300))
        val map2 = map1.map {
            case (a, (_, b)) => (a + 1, b + 1)
        }
        println(map2)
        //调用apply方法
        (f1 _).apply(100)
        val f = f1 _
        f.apply(100)
    }

    /**
     * 返回一个偏函数
     *
     * @return PartialFunction[Int, Int]
     */
    def f0: PartialFunction[Int, Int] = {
        case a => 10
    }

    def f1(a: Int) = println(a)
}
