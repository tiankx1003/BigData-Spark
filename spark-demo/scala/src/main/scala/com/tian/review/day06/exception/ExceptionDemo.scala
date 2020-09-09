package com.tian.review.day06.exception

/**
 * @author tian
 *         2019/9/10 18:22
 */
object ExceptionDemo {
    def main(args: Array[String]): Unit = {
        Thread.sleep(1000) //scala中不需要捕获异常
        println("Hello World")
        try {
            val a = 1 / 0
            throw new IllegalArgumentException("参数不合法")
        } catch {
            case e: ArithmeticException => println(e)
            case e: RuntimeException => println(e)
            case e: Exception => println(e)
            case _ =>
        } finally {
            println("finally")
        }
        f1()
    }

    @throws(classOf[IllegalArgumentException])
    def f0(a: String): Unit = println(a)

    def f1(): Unit = f0("String")
}
