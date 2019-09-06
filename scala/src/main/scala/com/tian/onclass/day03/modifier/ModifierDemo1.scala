package com.tian.onclass.day03.modifier

/**
 * @author tian
 *         2019/9/6 14:09
 */
/*
java中权限修饰符protected修改的方法，不在同一个包中继承后的访问super.foo()
scala中的访问权限有三种
默认(public,没有public关键字)
protected 只能子父类，同包内不能访问
private 定制private包


 */
object ModifierDemo1 {

}

class A {
    //访问权限的定制，当前包及所有子包都能访问
    private[modifier] def foo() = {

    }
}

class B extends A{
    val a = new A
    a.foo() //定制后可访问
}
