package com.tian.review.day05.pattern

/**
 * @author tian
 *         2019/9/10 8:31
 */
object PatternTuple {
    def main(args: Array[String]): Unit = {
        val tuple = Tuple3(1,3,4)
        tuple match {
            case (1,3,4) => println(tuple)
            case (1,_,4) => println("head and tail")
        }
    }
}
