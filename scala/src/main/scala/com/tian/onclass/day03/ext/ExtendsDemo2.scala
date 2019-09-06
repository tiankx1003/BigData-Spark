package com.tian.onclass.day03.ext

/**
 * @author tian
 *         2019/9/6 10:56
 */
object ExtendsDemo2 {

}

class AA(val n:Int)

class BB() extends AA(10){ // 必须通过传值的方式调用父类的主构造
    override val n: Int = 100
}
