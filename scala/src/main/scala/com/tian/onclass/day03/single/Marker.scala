package com.tian.onclass.day03.single

import scala.collection.mutable

/**
 * @author tian
 *         2019/9/6 15:07
 */
object Marker {
    var markers = mutable.Map(
        "red" -> new Marker("red"),
        "blue" -> new Marker("blue"),
        "yellow" -> new Marker("yellow"))

    def getMarker(color: String) = {
        markers.getOrElseUpdate(color,new Marker(color))
    }

    def main(args: Array[String]): Unit = {
        println(Marker.getMarker("red"))
    }
}

class Marker private (val color: String) {//私有化主构造
    println(s"$color marker")

    override def toString: String = s"$color"
}