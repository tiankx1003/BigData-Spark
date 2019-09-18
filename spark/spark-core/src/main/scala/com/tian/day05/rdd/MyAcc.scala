package com.tian.day05.rdd

import org.apache.spark.util.AccumulatorV2

import scala.tools.cmd.Spec.Accumulator

/**
 * @author tian
 * @date 2019/9/18 9:20
 * @version 1.0.0
 */
object MyAcc {

}

class MyAcc extends AccumulatorV2[Long, Long] {
    var sum = 0L //缓存中间值
    /**
     * 判断零值
     * 零值不一定是数字0，根据具体情况而定
     *
     * @return
     */
    override def isZero: Boolean = sum == 0

    /**
     * 复制累加器
     * 累加器从driver发送到executor
     *
     * @return 返回一个累加器
     */
    override def copy(): AccumulatorV2[Long, Long] = {
        val newAcc = new MyAcc
        newAcc.sum = sum //把当前缓存的值赋值给新的acc
        newAcc
    }

    /**
     * 重置累加器
     * 把缓存值重置为零值
     */
    override def reset(): Unit = sum = 0

    /**
     * 核心功能: 累加
     *
     * @param v
     */
    override def add(v: Long): Unit = sum += v

    /**
     * 合并合并累加器
     *
     * @param other
     */
    override def merge(other: AccumulatorV2[Long, Long]): Unit = {
        //this.sum += other.asInstanceOf[MyAcc].sum
        other match { //模式匹配写法
            case o: MyAcc => this.sum += o.sum
            case _ => throw new IllegalStateException
        }
    }

    /**
     * 返回最终累加后的值
     *
     * @return
     */
    override def value: Long = this.sum

}
