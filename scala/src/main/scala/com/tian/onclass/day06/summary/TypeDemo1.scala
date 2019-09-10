package com.tian.onclass.day06.summary

/**
 * @author tian
 *         2019/9/10 14:06
 */
/*
Any
AnyVal
AnyRef
Nothing

Option
    空指针问题
    Some
    None

either
    用于表示正确或者错误的结果

类型推断
    1.推得类型和预期的不一致 val a: Double = 1
    2.递归函数的返回值不能推导
    3.函数属性如果使用 _ 作为默认值，不能推导
    4.当显式使用return，返回值类型不能推导

_ 的使用
    1.通配符导包或类
    2.匿名函数的简写
    3.部分引用函数 val sqrt = math.pow(_,2)
    4.元组的元素 t._1 t._2
    5.模式匹配中的通配匹配
    6.利用_给属性设置默认值
    7.传递函数而不调用 val f = foo _
    8.异常catch
    9.分解操作，rest:_* , rest@_*
    10.用于定义标识符中包含 :  foo_:


方法的约定
    运算符结合性


apply和update

    apply:
        1.在伴生对象中，伴生对象(...) 其实就是在调用伴生对象的apply方法
        2.在伴生类中
        3.用apply调用方法

    update:
        更新属性值
 */
object TypeDemo1 {
    def main(args: Array[String]): Unit = {
        //获取Option返回值
        val some = f0()
        println(some.get)
        println(some.getOrElse(100)) //防止空值

        some match{ //使用模式匹配也能解决返回控制的问题
            case Some(a) => println(a)
            case None =>
        }

        f_:()
    }

    def f0():Option[Int] = {
        Some(10)
        None
    }

    def f_:() = println("a")


}

sealed trait test //sealed修饰后的类或trait子类或实现类只能在当前文件声明
