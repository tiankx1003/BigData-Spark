package com.tian.review.day02.onclass

/**
 * @author tian
 *         2019/9/4 11:38
 */
object HighDemo2 {
    def main(args: Array[String]): Unit = {
        operation(Array(1,2,3),op)
        operation(Array(1,2,3),(ele:Int) => { //匿名函数
            print(ele)
        }) // 匿名函数函数体只有一行大括号可以省略，
        // 参数类型可自动推导(可以省略)，极个别情况无法推导
        // 只有一个参数时可以省略圆括号
        operation(Array(1,2,3),ele => print(ele))
        operation(Array(1,2,3),print(_)) // 参数只使用一次的最简形式


    }

    /**
     * 作为被调用的函数，实际使用次数可能很少，可以使用匿名函数代替
     * @param ele
     */
    def op(ele:Int) = { //只需要在算子内写业务逻辑，
//        -ele
    }

    /**
     * 高阶函数的存在，减少了代码冗余
     * 在实际调用operation时根据实际传入的op对应的函数决定业务逻辑
     * @param arr
     * @param op 被传入的函数，只写定被传入函数的参数类型和返回值类型，类似java中的接口
     */
    def operation(arr:Array[Int],op:Int => Unit): Unit =
        for (elem <- arr)
            op(elem)

}
