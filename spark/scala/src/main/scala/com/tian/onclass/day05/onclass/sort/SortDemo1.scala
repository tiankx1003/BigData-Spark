package com.tian.onclass.day05.onclass.sort

import scala.collection.mutable.ArrayBuffer

/**
 * sort排序算子
 *
 * @author tian
 *         2019/9/9 9:59
 */
/*
排序算法
冒泡、选择、插入
归并、希尔、快速
*当快排的数据量小到一定规模后，会自动转为插入

三个支持排序的排序算子
sorted
sortBy
sortWith
排序都是产生一个新的集合，不会在原结合修改

排序的本质就是比较元素的大小
1.让元素本身具有其他元素比较的能力(实现Comparable接口，重写CompareTo(other) this other) -- Ordered @ scala
2.第三方比较结构来进行比较大小(Comparator，Compare(o1,o2)) -- Ordering @ scala
* Comparator优先级高，都存在时以Comparator为准
自定义类型比较
 */
object SortDemo1 {
    def main(args: Array[String]): Unit = {
        val list1 = List(10, 20, 40, 22, 23)
        val sortedList1 = list1.sorted
        println(list1)
        println(sortedList1)
        val list2 = ArrayBuffer(10, 20, 22, 11, 32)
        val sortedArray = list2.sorted
        println(sortedArray)
        println(list2)
        //自定义类型
        val users1 = List(new User1(20, "lisid"), new User1(15, "wangwu"), new User(12, "wanger"))
//        println(users1.sorted)
        val users = List(new User(20, "lisid"), new User(15, "wangwu"), new User(12, "wanger"))
        println(users(0) > users(1)) //实现Ordered可以直接使用> 和 < 比较
        println(users.sorted)
        //def sorted[B >: A](implicit ord: Ordering[B]): Repr
        list1.sorted(Ordering.Int) //升序，默认
        list1.sorted(Ordering.Int.reverse) //降序
        /*println(users1.sorted(new Ordering[User2] {
            override def compare(x: User2, y: User2): Int = x.age - y.age
        }.reverse)) //按照年龄属性比较，反向*/
    }
}

class User(val age: Int, val name: String) extends Ordered[User] {
    override def compare(that: User): Int = {
        var r = this.age - that.age
        if (r == 0) r = this.name.compareTo(that.name)
        r
    }
}

/**
 * 实现Ordering可以自定义排序规则
 *
 * @param age
 * @param name
 */
class User2(val age: Int, val name: String)

class User1(val age: Int, val name: String) extends Comparable[User] {
    override def compareTo(o: User): Int = {
        var r = this.age - o.age
        if (r == 0) r = this.name.compareTo(o.name)
        r
    }
}