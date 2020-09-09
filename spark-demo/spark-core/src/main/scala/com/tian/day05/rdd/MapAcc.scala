package com.tian.day05.rdd

import org.apache.spark.util.AccumulatorV2


/**
 * @author tian
 * @date 2019/9/18 10:08
 * @version 1.0.0
 */
class MapAcc extends AccumulatorV2[Long, Map[String, Double]] {
    var map = Map[String, Double]()
    var count = 0L //记录累加的元素个数

    override def isZero: Boolean = map.isEmpty

    override def copy(): AccumulatorV2[Long, Map[String, Double]] = {
        var acc = new MapAcc
        acc.map = map
        acc.count = count
        acc
    }

    override def reset(): Unit = {
        map = Map[String, Double]()
        count = 0
    }

    override def add(v: Long): Unit = {
        // sum max min
        count += 1
        map += "sum" -> (map.getOrElse("sum", 0D) + v)
        map += "max" -> map.getOrElse("max", Long.MinValue.toDouble).max(v)
        map += "min" -> map.getOrElse("min", Long.MaxValue.toDouble).min(v)
    }

    override def merge(other: AccumulatorV2[Long, Map[String, Double]]): Unit = {
        other match {
            case o: MapAcc =>
                count += o.count
                map += "sum" -> (map.getOrElse("sum", 0D) + o.map.getOrElse("sum", 0D))
                map += "max" -> (map.getOrElse("max", Long.MinValue.toDouble).max(o.map.getOrElse("max", Long.MinValue.toDouble)))
                map += "min" -> (map.getOrElse("min", Long.MaxValue.toDouble).max(o.map.getOrElse("min", Long.MaxValue.toDouble)))
            case _ => throw new UnsupportedOperationException
        }
    }

    override def value: Map[String, Double] = {
        map += "avg" -> map.getOrElse("sum", 0D) / count
        map
    }
}
