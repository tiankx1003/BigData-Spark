package com.tian.onclass.day04.map

/**
 * 不可变Map
 * @author tian
 *         2019/9/7 11:27
 */
object MapDemo1 {
    def main(args: Array[String]): Unit = {
        //scala.Predef._ 默认导入
        val map = Map("a" -> 97, "b" -> 98, "c" -> 99, "d" -> 100)
        println(map("a"))
        //        println(map("f")) //没有这个key则抛出异常
        println(map.getOrElse("f", 102)) //不存在则返回默认值
        for (kv <- map) {
            println(kv) //kv是元组，scala中的map底层是元组
            println(kv._1) //打印key
            println(kv._2) //打印value
        }
        for ((k, v) <- map) {
            println(k)
            println(v)
        }
        for ((_, v) <- map) { //模式匹配
            println(v)
        }
        for ((k, 99) <- map) { //只返回value是99的
            println(k)
        }
        for ((k, v) <- map if v < 99){ //守卫
            println(k)
        }
        // -> 就是tuple2函数
        val map2 = Map((1,10),(2,30)) //元组的方式声明map
        println(map)
    }
}
