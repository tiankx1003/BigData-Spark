package com.tian.day01.udf

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._

/**
 * 求平均值
 *
 * @author tian
 * @date 2019/9/20 19:34
 * @version 1.0.0
 */
class MyAvg extends UserDefinedAggregateFunction {
    override def inputSchema: StructType = StructType(StructField("column", DoubleType) :: Nil)

    override def bufferSchema: StructType =
        StructType(StructField("sum", DoubleType) :: StructField("count", LongType) :: Nil)

    override def dataType: DataType = DoubleType

    override def deterministic: Boolean = true

    override def initialize(buffer: MutableAggregationBuffer): Unit = {
        buffer(0) = 0D //缓存总和
        buffer(1) = 0L //缓存总数
    }

    override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
        buffer(0) = buffer.getDouble(0) + input.getDouble(0)
        buffer(1) = buffer.getLong(1) + 1L
    }

    override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
        buffer1(0) = buffer1.getDouble(0) + buffer2.getDouble(0)
        buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
    }

    override def evaluate(buffer: Row): Double =
        if (buffer.getLong(1) != 0) buffer.getDouble(0) / buffer.getLong(1) //返回平均值
        else 0D //个数为0
}
