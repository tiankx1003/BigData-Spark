package com.tian.review.day02.highfun.fun

/**
 * @author tian
 *         2019/9/4 18:56
 */
object TestFun {
    /**
     * 至简原则
     *
     * @param args
     */
    def main(args: Array[String]): Unit = {
        //0.标准写法
        def f0(s: String): String = {
            return s + "String"
        }

        //1.return可以省略，scala使用函数体的最后一行代码作为返回值
        //注:如果return没有省略，那么返回值类型也不能省略，同f0
        def f1(s: String): String = {
            s + "String"
        }

        //2.返回值的类型如果可以推断(根据方法体的最后一行代码)出来，也可以省略
        def f2(s: String) = {
            s + "String"
        }

        //3.如果函数体只有一行代码，也可以省略花括号
        def f3(s: String) = s + "String"

        //4.如果函数没有参数列表，那么小括号可以省略(调用时必须也省略)
        def f4 = "String"

        val a = f4

        //5.如果函数签名明确了返回值为Unit，return不生效
        def f5: Unit = return 10

        val b = f5 //Unit
        //6.如果省略等号，则自动推断返回值为Unit，这时return也不生效
        def f6 {
            print("Hello World!")
            return 10
        }

        //7.如果不关心函数名，只关心逻辑关系，则可省略函数名(和def) -- 匿名函数
        val f = (s: String) => return s + "String"
    }
}
