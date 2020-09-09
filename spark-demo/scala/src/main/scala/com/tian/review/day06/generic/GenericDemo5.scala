package com.tian.review.day06.generic

/**
 * 泛型的不变、协变、逆变
 *
 * @author tian
 *         2019/9/10 19:39
 */
object GenericDemo5 {
    def main(args: Array[String]): Unit = {
        val arr1 = Array(10, 20)
        //val arr2: Array[Double] = arr1 //类型不匹配
        val bs = Array(new B)
        //val as: Array[A] = bs //不变，类型不匹配
        val cb = new C[B]
        //val ca: C[A] = cb //不变
        val list: List[A] = List[B]()
        val db = new D[B]
        val da: D[A] = db //协变
        val ea = new E[A]
        val eb: E[B] = ea //逆变
    }
}
class A
class B extends A
class C[T] //不变
class D[+T] //协变
class E[-T] //逆变
