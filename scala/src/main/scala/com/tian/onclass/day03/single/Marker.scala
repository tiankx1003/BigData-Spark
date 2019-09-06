package com.tian.onclass.day03.single

import scala.collection.mutable

/**
 * @author tian
 *         2019/9/6 15:07
 */
// TODO 工厂类
object Marker {
    val markers = mutable.Map(
        "red" -> new Marker("red"),
        "blue" -> new Marker("blue"),
        "yellow" -> new Marker("yellow"))

    def getMarker(color: String) = {
        markers.getOrElseUpdate(color,new Marker(color))
    }

    def main(args: Array[String]): Unit = {
        println(Marker.getMarker("red"))
        println(Marker.getMarker("yellow"))
        println(Marker.getMarker("blue"))
    }
}

class Marker private (val color: String) {//私有化主构造
    println(s"$color marker")

    override def toString: String = s"$color"
}