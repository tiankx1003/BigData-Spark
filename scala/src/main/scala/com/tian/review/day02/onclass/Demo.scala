package com.tian.preview.day02.onclass

/**
 * @author tian
 *         2019/9/4 11:00
 */
object Demo {
    def main(args: Array[String]): Unit = {
        println(sumPrime(100, 1000))
    }
    def sumPrime(start:Int,end:Int)={
        var sum = 0
        for(i<-start to end;j <- 2 until i if(i%j != 0)) sum += i
        sum
    }
}
