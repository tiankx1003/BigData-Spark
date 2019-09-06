package com.tian.onclass.day03.traitdemo.sub

/**
 * 动态叠加(动态混入)
 * @author tian
 *         2019/9/6 16:28
 */
object TraitDemo5 {
    def main(args: Array[String]): Unit = {
        val c:C = new C
        val a:A = new C
        val b:B = new C with B //动态叠加
    }
}
trait A
trait B
class C extends A
