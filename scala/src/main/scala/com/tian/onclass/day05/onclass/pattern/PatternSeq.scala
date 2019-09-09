package com.tian.onclass.day05.onclass.pattern

/**
 * @author tian
 *         2019/9/9 17:07
 */
object PatternSeq {
    def main(args: Array[String]): Unit = {
        val names = "lisi,za,ww,zl"
        names match {
            case Names(one, two, a@_*) => println(one, two)
        }
    }
}

object Names { //匹配序列，Some里面返回的是一个集合
    def unapplySeq(s: String) = {
        if (s.length == 0) None
        else Some(s.split(","))
    }
}
