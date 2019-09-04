package com.tian.preview.day02.onclass

/**
 * @author tian
 *         2019/9/4 15:44
 */
/*
控制抽象

值调用:把计算后的值传递过去，java中只有值调用
名调用:把代码(或表达式)传递过去，没调用一次就执行一次

 */
object ControlAbs {
    def main(args: Array[String]): Unit = {
        def f = () =>{
            println("f....")
            10
        }
        foo(3 + 4) //本质上传入的不是7，传入的是7
        foo(f())
        //TODO 使用代码块改写
        while(true){

        }
        /*
        myWhile(i > 1){

        }
        */
    }
    def foo(a:Int) ={
        println(a)
        println(a)
        println(a)
    }
}
