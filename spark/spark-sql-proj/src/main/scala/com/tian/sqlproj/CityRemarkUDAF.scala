package com.tian.sqlproj

import java.text.DecimalFormat

import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._
import org.apache.spark.sql.{Row, types}

/**
 * @author tian
 * @date 2019/9/21 15:23
 * @version 1.0.0
 */
class CityRemarkUDAF extends UserDefinedAggregateFunction {
    override def inputSchema: StructType = StructType(StructField("city_name", StringType) :: Nil)

    override def bufferSchema: StructType = //缓存城市统计和总数
        StructType(StructField("city_count", types.MapType(StringType, LongType)) :: StructField("total", LongType) :: Nil)

    //最终返回所有信息拼接成的字符串，北京21.2%，天津13.2% ...
    override def dataType: DataType = StringType

    override def deterministic: Boolean = true

    override def initialize(buffer: MutableAggregationBuffer): Unit = {
        buffer(0) = Map[String, Long]() //北京 -> 1000  天津 -> 200 ...
        buffer(1) = 0L //某地区某个商品的总点击量
    }

    override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
        if (!input.isNullAt(0)) {
            val cityName = input.getString(0)
            buffer(1) = buffer.getLong(1) + 1L
            var cityCountMap = buffer.getMap[String, Long](0)
            cityCountMap += cityName -> (cityCountMap.getOrElse("cityName", 0L) + 1L)
            buffer(0) = cityCountMap
        }
    }

    override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
        val map1 = buffer1.getMap[String, Long](0)
        val map2 = buffer2.getMap[String, Long](0)
        //把map2的数据合并到map中，然后在重新赋值到buffer1(0)
        val resultMap = map2.foldLeft(map1) {
            case (map, (cityName, count)) =>
                map + (cityName -> (map.getOrElse(cityName, 0L) + count))
        }
        buffer1(0) = resultMap
        buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
    }

    override def evaluate(buffer: Row): Any = {
        val cityCountMap = buffer.getMap[String, Long](0)
        val total = buffer.getLong(1)
        var top2List = cityCountMap
            .toList
            .sortBy(-_._2)
            .take(2)
            .map {
                case (cityName, count) => CityRemark(cityName, count.toDouble / total)
            }
        top2List :+= CityRemark("其他", top2List.foldLeft(1D)((rate, cr) => rate - cr.rate))
        top2List.mkString(", ")
    }
}

// TODO: 最终计算结果有误
case class CityRemark(cityName: String, rate: Double) {
    private val formatter = new DecimalFormat("0.00%")

    override def toString: String = s"$cityName:${formatter.format(rate)}"
}