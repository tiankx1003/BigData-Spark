package com.tian.review.day02.onclass

/**
 * @author tian
 *         2019/9/4 9:19
 */

/*
函数式编程
函数可以当作一个值进行传递，高阶函数
scala中函数式编程和面向对象编程完美融合在一起
 */
object FunDemo1 {
    def main(args: Array[String]): Unit = {
        //函数的调用
        add(3, 4)
        add2(3, 4)
        foo //只有一个形参或没有形参，可以省略圆括号
        foo1 //没有形参时如果定义省略了圆括号，那么调用时也要省略圆括号
        add3(Array(1, 2): _*) //拆解数组序列 Array(1,2,3):_*
        add3(1 to 100: _*)
    }

    //函数的语法格式
    /*
    函数签名三要素:函数名，形参列表，返回值类型
    函数体:内可以没有return，自动把代码最后一行的值返回
        返回值类型省略后根据最后一行代码的值类型推导
        当没有省略返回值类型时必须写明返回值类型
        = 省略时表示函数返回的一定是Unit，无论函数体怎么定义，一定隐式返回Unit，有时候称为过程


    纯函数:
        特点:1.不产生副作用(如控制台打印，修改了外部变量的值，向磁盘写入文件等)
            2.引用透明，函数的返回值只和形参有关，和其他任何值无关
        优点:1.天然的支持高并发
            2.计算速度更快(计算的结果直接放进缓存)
    过程:只有副作用，没有返回值
     */
    def add(a: Int, b: Int): Int = {
        //函数的具体实现
        a + b //省略return自动把代码最后一行的值返回
    }

    def add2(a: Int, b: Int) = {
        //函数的具体实现
        a + b //省略return自动把代码最后一行的值返回
    }

    def foo(): Unit = {

    }

    def foo1: Unit = {
        println("foo1...")
    }

    val foo2 = { //看作静态的，在第一次加载object时调用
        print("foo2...")
    }

    def add3(arr: Int*): Unit = { // 可变参数，可变部分放最后
        var sum = 0
        for (elem <- arr) {
            sum += elem
        }
        println(sum)
    }
    def add4(a:Int,arr:Int*): Unit ={ // 可变参数前添加一个参量

    }

}
