package com.tian.review.day04.high

/**
 * @author tian
 *         2019/9/12 8:47
 */
object FilterDemo {
    def main(args: Array[String]): Unit = {
        val list1 = List(1, 2, 3, 4, 5)
        val list2 = list1.filter(_ % 2 == 0) //只保留偶数
        val list3 = list1.filter(_ % 2 == 0).map(x => x * x)

        val list4 = List(null, 20, 30, 40, true, "aaa")
        val list5 = list4.filter(_.isInstanceOf[Int])
            .map(_.asInstanceOf[Int] + 1) //转型
            .map(_ + 1)
    }
}
