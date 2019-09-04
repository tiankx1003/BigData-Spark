package com.tian.preview.day02.onclass.obj

import scala.beans.BeanProperty

/**
 * @author tian
 *         2019/9/4 16:36
 */
/*
java中的类
1.如果类是public的，则必须和文件名一致
2.一般，一个.java有一个public类
 */
class User(@BeanProperty name:String, val age:Int, sex :String){
    //@BeanProperty注解能够生成符合JavaBean规范的getter和setter，根据var或val或没有指定，有所不同
    //sex没有指定var或者val，成为私有属性，且没有公共的setter和getter方法
    //同时定义了构造器，构造方法内的参量自动成为私有属性，
    // 而且隐含了对应属性的getter setter，如果定义的是val，则没有setter
    //如果类中没有代码，可以省略大括号
    def foo={
        //println(name) //闭包，访问的是变量
        println(this.name) //站在面向对象的角度，name为属性
        val name = "aaa" //如果定义了同名变量，向使用外部name,只能用this.name
    }


}
