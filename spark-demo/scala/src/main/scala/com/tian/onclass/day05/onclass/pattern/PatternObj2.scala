package com.tian.onclass.day05.onclass.pattern

/**
 * @author tian
 *         2019/9/9 16:45
 */
object PatternObj2 {
    def main(args: Array[String]): Unit = {
        val r: Double = 9
        r match {
            //            case My(a) => println(a)
            case _ =>
        }
    }
}

object My {
    def unapply(arg: Double) = new My(arg)
}

class My(r: Double) {
    //    def isEmpty = if(r >= 0) false else true
    def ifEmpty = r < 0

    def get = math.sqrt(r)
}
