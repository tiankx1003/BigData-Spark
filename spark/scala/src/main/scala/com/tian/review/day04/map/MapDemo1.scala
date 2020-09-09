package com.tian.review.day04.map

/**
 * Map
 *
 * @author tian
 *         2019/9/11 21:01
 */
object MapDemo1 {
    def main(args: Array[String]): Unit = {
        //scala.Predef._ 默认导入
        val map1 = Map("a" -> 97, "b" -> 98, "c" -> 99)
        val va = map1("a") //返回对应的value
        //val vf = map1("f") //当没有匹配的key时抛异常
        val vf = map1.getOrElse("f", 102) //当key不存在时返回默认值
        for (kv <- map1) { //kv是元组
            println(kv)
            println(kv._1) //key
            println(kv._2) //value
        }
        for ((k, v) <- map1) {
            println(k)
            println(v)
        }
        for ((_, a) <- map1) println(a)
        for ((k, 99) <- map1) println(k) //只返回value为99的值
        for ((k, v) <- map1 if v < 99) println(k) //守卫
        val map2 = Map((1, 10), (2, 20)) // -> 相当于tuple2函数
    }

}
