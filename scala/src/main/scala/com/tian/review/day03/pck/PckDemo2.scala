package com.tian.review.day03.pck

import java.util //和java中一样格式的导包
import java.util.{ArrayList => JAL} //别名
import java.util._ //通配符导包
import java.util.{ArrayList => _, _} // 屏蔽类ArrayList
import java.util.{ArrayList, List} //导入包中的ArrayList和List类
/**
 * 导包
 *
 * @author tian
 *         2019/9/6 19:11
 */
object PckDemo2 {

    import java.util.List // 局部导包
    def main(args: Array[String]): Unit = {
        import java.util.ArrayList //局部导包
        new util.ArrayList[String]()
    }
}
