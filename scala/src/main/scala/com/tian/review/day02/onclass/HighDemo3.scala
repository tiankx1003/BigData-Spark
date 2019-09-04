//package com.tian.preview.day02.onclass
//
///**
// * @author tian
// *         2019/9/4 14:11
// */
///*
//高阶函数实现map操作
//
// */
//object HighDemo3 {
//    def main(args: Array[String]): Unit = {
//        val arr = map[Int, String](Array(1, 2, 3, 4), x => x + "a")
//        println(arr.mkString(","))
//        val arr1 = filter(Array(1, 2, 3, 4), _ % 2 == 1) // 只保留奇数
//        val num = reduce(Array(1, 2, 3, 4), _ * _) // reduce操作为求积
//    }
//
//    def reduce(arr: Array[Int], op: (Int, Int) => Int) = {
//        var init = arr(0)
//        for (index <- 1 until arr.length) {
//            init = op(init, arr(index))
//        }
//        init
//    }
//
//    def filter(arr: Array[Int], op: Int => Boolean) = {
//        for (elem <- arr if op(elem)) yield elem // 只返回op操作为true的值
//    }
//    //TODO 报错
//    def map[T, S](arr: Array[T], op: T => S): Array[S] = {
//        for (elem <- arr) yield op(elem) //for推导
//    }
//}
