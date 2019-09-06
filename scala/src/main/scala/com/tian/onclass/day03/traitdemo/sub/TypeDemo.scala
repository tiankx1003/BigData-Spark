package com.tian.onclass.day03.traitdemo.sub

/**
 * 给类起别名
 * @author tian
 *         2019/9/6 16:33
 */
object TypeDemo {
    def main(args: Array[String]): Unit = {
        type P = joasjdogansngpasngoans
        val p = new P
        println(p.isInstanceOf[P])
        println(p.isInstanceOf[joasjdogansngpasngoans])
        println(p.getClass.getSimpleName)
    }
}

class joasjdogansngpasngoans