package com.tian.day01.udf

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, StructField, StructType}

/**
 * 求和
 *
 * @author tian
 * @date 2019/9/20 16:26
 * @version 1.0.0
 */
class MySum extends UserDefinedAggregateFunction {
    //定义聚合函数输入数据的类型,如果求平均值，需要缓存两个值
    override def inputSchema: StructType = StructType(StructField("column", DoubleType) :: Nil)

    //缓冲区的数据类型
    override def bufferSchema: StructType = StructType(StructField("sum", DoubleType) :: Nil)

    //最终的返回值类型
    override def dataType: DataType = DoubleType

    //确定性，指相同的输入是否应该返回相同的输出
    override def deterministic: Boolean = true

    //初始化，定义缓冲区的零值
    override def initialize(buffer: MutableAggregationBuffer): Unit = buffer(0) = 0d


    /**
     * 分区内的聚合
     *
     * @param buffer 缓冲区，用于存放计算结果
     * @param input  需要聚合的输入
     */
    override def update(buffer: MutableAggregationBuffer, input: Row): Unit =
        buffer(0) = buffer.getDouble(0) + input.getAs[Double](0) //buffer取值必须指明类型

    /**
     * 分区间的聚合
     *
     * @param buffer1 最终运算结果赋值给buffer1
     * @param buffer2
     */
    override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit =
        buffer1(0) = buffer1.getDouble(0) + buffer2.getDouble(0)

    /**
     * 返回最终的值
     *
     * @param buffer
     * @return 最好指定返回值类型
     */
    override def evaluate(buffer: Row): Double = buffer.getDouble(0)
}
