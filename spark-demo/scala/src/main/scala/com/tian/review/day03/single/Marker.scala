package com.tian.review.day03.single

import scala.collection.mutable

/**
 * 工厂类
 *
 * @author tian
 *         2019/9/9 18:30
 */
object Marker {
    val markers = mutable.Map(
        "red" -> new Marker("red"),
        "blue" -> new Marker("blue"),
        "yellow" -> new Marker("yellow")
    )
    def getMarker(color:String) ={
        markers.getOrElseUpdate(color,new Marker(color))
    }
    def main(args: Array[String]): Unit = {
        println(Marker.getMarker("red"))
        println(Marker.getMarker("yellow"))
        println(Marker.getMarker("blue"))
    }
}

class Marker private (val color:String){ //私有化构造器
    println(s"$color marker")
    override def toString: String = s"$color"
}
