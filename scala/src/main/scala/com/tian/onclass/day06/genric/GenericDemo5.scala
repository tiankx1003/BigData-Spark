package com.tian.onclass.day06.genric

/**
 * 泛型的三变:不变，协变，逆变
 * java中只有不变
 *
 * 协变点、逆变点(略)
 *
 * @author tian
 *         2019/9/10 11:39
 */
object GenericDemo5 {
    def main(args: Array[String]): Unit = {
        /*
        不变
        不能把子类型对象的集合赋值给父类型的集合
         */
        val arr1 = Array(10, 20)
        //        val arr2:Array[Double] = arr1 //类型不匹配
        val bs = Array(new B)
        //        val as:Array[A] = bs
        val cb = new C[B]
        //        val ca: C[A] = cb //不变

        /*
        协变
        子类型的集合赋值给父类型的集合
         */
        //type List[+A] = scala.collection.immutable.List[A]
        val list: List[A] = List[B]() //子类型的集合赋值给父类引用的集合
        val db = new D[B]
        val da: D[A] = db //协变

        /*
        逆变
        父类型的集合赋值给子类型的集合
         */
        val ea = new E[A]
        val eb: E[B] = ea //逆变
    }
}

class A

class B extends A

class C[T]

class D[+T] { //协变(covariant)，
    //T作为协变不能用在逆变点，可以通过添加
    //    def f(a:T) = {}
//    def f1(): T =

}

class E[-T] //逆变