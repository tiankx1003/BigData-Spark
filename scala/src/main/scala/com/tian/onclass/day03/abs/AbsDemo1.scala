package com.tian.onclass.day03.abs

/**
 * @author tian
 *         2019/9/6 11:32
 */
/*
java中类属性的默认值
    数字类型默认值为0
    引用类型默认值为null
    布尔类型默认值为false

抽象类
    普通类有，抽象类都可以有，
    抽象方法和抽象字段(属性)

property，包含所有field(字段) method(方法)




*/
object AbsDemo1 {

}

abstract class Human(val name:String){
    val age:Int = 0 //无法从外部传参
    var addr:String = _ //让jvm赋值为默认值
    var sex:String //抽象字段，只声明，不初始化，只能在抽象类和接口中使用
    def say(a:Int):Int //抽象方法，只有签名没有实现，不写返回值类型时为Unit
    val a:Int //常量也可以是抽象的
}

// 这时有报错，因为val不能覆写var，
/*
class Person(override val name:String,override val age:Int)extends Human(name) {
    override var sex: String = _
    override def say(a: Int): Int = 10
    override val a: Int = 10
}
*/

// TODO 修改
class Person2(override val name:String,override val age:Int)extends Human(name) {
    override var sex: String = _
    override def say(a: Int): Int = 10
    override val a: Int = 10
}


