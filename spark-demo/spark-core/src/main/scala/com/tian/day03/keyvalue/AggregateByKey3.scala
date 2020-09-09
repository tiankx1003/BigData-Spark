package com.tian.day03.keyvalue

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author tian
 * @date 2019/9/16 10:47
 * @version 1.0.0
 */
object AggregateByKey3 {
    def main(args: Array[String]): Unit = {
        def main(args: Array[String]): Unit = {
            val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
            val sc = new SparkContext(conf)
            val rdd1 = sc.parallelize(List(("a", 2), ("a", 5), ("c", 4), ("b", 3), ("c", 6), ("c", 8)), 2)
            //宽依赖，
            //分区内和分区间聚合逻辑不同
            rdd1.aggregateByKey(Int.MinValue)(math.max(_, _), _ + _) //部分应用函数
            rdd1.aggregateByKey(Int.MinValue)((x, y) => x.max(y), _ + _)
            val rdd2 =
                rdd1.aggregateByKey(
                    (Int.MinValue, Int.MaxValue))((maxMin, v) => (maxMin._1.max(v), maxMin._2.min(v)),
                    (maxMin1, maxMin2) => (maxMin1._1 + maxMin2._1, maxMin1._2 + maxMin2._2)
                )
            rdd1.aggregateByKey((Int.MinValue, Int.MaxValue))(
                {
                    case ((max, min), v) => (max.max(v), min.min(v))
                },
                {
                    case ((max1, min1), (max2, min2)) => (max1 + max2, min1 + min2)
                }
            ) //返回key对应的value的最值
            rdd1.aggregateByKey((0, 0))(
                {
                    case ((sum, count), v) => (sum + v, count + 1)
                },
                {
                    case ((sum1, count1), (sum2, count2)) => (sum1 + sum2, count1 + count2)
                }
            ).map({ //map过程中没有操作key，只操作了value，可使用mapValues
                case (k, (sum, count)) => (k, sum.toDouble / count)
            }) //返回每个key对应的value平均值
            rdd1.aggregateByKey((0, 0))(
                {
                    case ((sum, count), v) => (sum + v, count + 1)
                },
                {
                    case ((sum1, count1), (sum2, count2)) => (sum1 + sum2, count1 + count2)
                }
            ).mapValues { //只对value进行map
                case (sum, count) => sum.toDouble / count
            } //同上
            sc.stop()
        }
    }
}
