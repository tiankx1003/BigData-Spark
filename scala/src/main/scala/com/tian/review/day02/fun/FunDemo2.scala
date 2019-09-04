package com.tian.review.day02.highfun.fun

/**
 * @author tian
 *         2019/9/4 19:30
 */
object FunDemo2 {
    def main(args: Array[String]): Unit = {
        println(sumPrime(100, 1000))
        println(sumPrime2(100, 1000))

        def f1(a: Int, b: Int, c: Int = 3) = a + b + c

        val m = f1(1, 2) // c为默认值3
        val n = f1(c = 1, b = 2, a = 2) //通过命令参量传值，可以不考虑顺序
        def f2(a: Int, b: Int = 3, c: Int = 4) = a + b + c

        val x = f2(3, c = 2) //f2的三个参量中有两个是默认值，传入两个参数，必须通过命名参量确定
        def f3(a: Int, b: Int = 2, c: Int) = a + b + c

        val y = f3(1, c = 2) // b有形参默认值，不指定命名参量，只传入两个默认第二个仍为b
    }

    def sumPrime(start: Int, end: Int) = {
        var sum = 0
        for (n <- start to end) {
            if (isPrime(n)) sum += n
        }
        sum
    }

    def isPrime(num: Int): Boolean = {
        for (i <- 2 until num) {
            if (num % i == 0) return false
        }
        true
    }

    // TODO 有问题
    def sumPrime2(start: Int, end: Int) = {
        var sum = 0
        for (i <- start to end; j <- 2 until i if i % j != 0)
            sum += i
        sum
    }

}
