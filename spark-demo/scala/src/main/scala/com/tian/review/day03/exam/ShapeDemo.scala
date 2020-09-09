package com.tian.review.day03.exam

/**
 * 定义一个抽象类 Shape，一个抽象方法 centerPoint，
 * 以及该抽象类的子类 Rectangle 和 Circle。
 * 为子类提供合适的构造器，并重写centerPoint方法,
 * 提供计算面积的方法 (根据需要添加相应的属性)
 *
 * @author tian
 *         2019/9/6 20:01
 */
object ShapeDemo {
    val s1 = new Rectangle(new Point(10,5),10,5,90)
    val s2 = new Circle(new Point(10,5),3)
    println(s1.area)
    println(s2.area)
}

abstract class Shape {
    def centerPoint: Point
}

class Rectangle(center:Point,width:Double,length:Double,angle:Double) extends Shape {
    override def centerPoint: Point = center
    def area = width * length
}

class Circle(center: Point, r: Double) extends Shape {
    override def centerPoint: Point = center
    def area = math.Pi * r * r
}


