package com.tian.onclass.day03.pck

import java.util
import java.util.{ArrayList => JAL} //别名
import java.util._
import java.util.{ArrayList => _, _}
import java.util.{ArrayList, List}

/**
 * @author tian
 *         2019/9/6 10:01
 */
/*
声明包
    1.和java一样
    2.包语句(使同一.scala文件中的class出于不同的包)
    * 具体使用还是按照java中的编写规范

导包
    1.和java一样，可以使用import导入(这个文件中的所有类都可以使用)
    2.局部导入(在使用的时候导入)
    3.通配符导入
    4.类别名解决类的重名问题
    5.屏蔽类，排除包中的某个类

 */
object PckDemo1 {
    import java.util.ArrayList
    def main(args: Array[String]): Unit = {
        import java.util.ArrayList
        new util.ArrayList[String]()
    }
}

class A

//package com.tian.onclass.day03.pck.sub
package sub {
    class B
    class C
    package sub1 {
        class D
        class A {
            def foo = {
                val b: B = null //使用父包不用导包
            }
        }
    }
}

