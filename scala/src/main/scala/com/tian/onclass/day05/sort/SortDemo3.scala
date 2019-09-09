package com.tian.onclass.day05.sort

/**
 * sortWith
 *
 * @author tian
 *         2019/9/9 10:53
 */
object SortDemo3 {
    def main(args: Array[String]): Unit = {
        val list1 = List(23, 22, 123, 11)
        println(list1.sortWith(_ < _)) //升序
        println(list1.sortWith(_ > _)) //降序
        list1.sortWith((x, y) => { //业务逻辑
            null
        })
    }
}
