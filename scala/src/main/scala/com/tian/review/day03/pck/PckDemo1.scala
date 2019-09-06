package com.tian.review.day03.pck

/**
 * 包的声明
 * @author tian
 *         2019/9/6 19:09
 */
object PckDemo1 {

}

package sub {

    class B

    class C
    package sub1 {

        class D

        class A {
            def foo: Unit = {
                val b: B = null //使用父包中的内容不用导包
            }
        }

    }

}
