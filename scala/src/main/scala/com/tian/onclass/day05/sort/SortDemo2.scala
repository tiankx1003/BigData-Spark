package com.tian.onclass.day05.sort

/**
 * sortBy
 *
 * @author tian
 *         2019/9/9 10:36
 */
object SortDemo2 {
    def main(args: Array[String]): Unit = {
        val list1 = List(10, 22, 43, 18, 29)
        val list1asc = list1.sortBy(_)
        val list1desc = list1.sortBy(x => x)(Ordering.Int.reverse)
        val list2 = List("aa", "agaga", "aga", "s")
        println(list2.sortBy(_.length))
        println(list2.sortBy(-_.length))
        println(list2.sortBy(_.length)(Ordering.Int.reverse))
        val users = List(new User(20, "lisid"), new User(15, "wangwu"), new User(12, "wanger"))
        println(users.sortBy(user => (user.age, user.name)))
        val usersSortBy = users.sortBy(user => (user.age, user.name))(Ordering.Tuple2(Ordering.Int, Ordering.String.reverse))
        println(usersSortBy) //为了解决指标过多的问题
    }
}
