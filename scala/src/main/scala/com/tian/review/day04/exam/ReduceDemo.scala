package com.tian.review.day04.exam


/**
 *
 * @author Friday
 * @version 1.0
 *          2019/9/9 0:35
 */
/*
1. 使用 reduce 计算集合中的最大值

2. 使用 foldLeft 同时计算最大值和最小值

3. 编写一个函数, 接收一个字符串, 返回一个 Map.
    比如: indexes("Helloee")

   返回: Map(H->{0}, e -> {1, 5, 6}, ...)   数字其实是下标

4. 实现一个函数，作用与mkString相同，使用foldLeft

5. 实现一个 scala 版的 wordcount
   读取文件, 然后实现
   Source.
 */
object ReduceDemo {
    def main(args: Array[String]): Unit = {
        println(reduceMax(List(1, 4, 6, 2)))
    }

    def reduceMax(arr: List[Int]) = arr.reduce(math.max(_, _))

    def maxMin(arr: List[Int]) = {
        arr.foldLeft()
    }
}


