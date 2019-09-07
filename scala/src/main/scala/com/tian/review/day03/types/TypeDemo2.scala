package com.tian.review.day03.types

/**
 * @author tian
 *         2019/9/7 8:44
 */
object TypeDemo2 {
    def main(args: Array[String]): Unit = {
        type P = aojaosigjaoisgaoijg
        val p = new P
        println(p.isInstanceOf[P])
        println(p.isInstanceOf[aojaosigjaoisgaoijg])
        println(p.getClass.getSimpleName)
    }
}
class aojaosigjaoisgaoijg