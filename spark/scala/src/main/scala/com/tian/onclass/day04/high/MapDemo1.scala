package com.tian.onclass.day04.high

/**
 * map算子，一进一出
 *
 * @author tian
 *         2019/9/7 14:29
 */
/*
map的作用:用来调整数据类型
 */
object MapDemo1 {
    def main(args: Array[String]): Unit = {
        val list1 = List(1, 2, 3, 4, 5)
        //final override def map[B, That](f: A => B)(implicit bf: CanBuildFrom[List[A], B, That]): That
        val list2 = list1.map(x => x * x)
        val list3 = list1.map(x => (x, x * x))
        println(list2)
        println(list3)
        val str = "abc".map(_ + 3)
        println(str)

        val lists = List(30, 50, 30)
        println(lists.map(x => x))
//        println(lists.map(_)) //TODO 见PartDemo部分引用函数
    }
}

/*
foreach 和 map的区别
foreach 仅仅是对集合遍历
map 不仅对集合遍历，而且返回结果集合
一个集合map之后，长度不会增加，也不会减少
 */
