

/**
 * @author tian
 * @date 2019/9/20 8:59
 * @version 1.0.0
 */
object SortBy {
    def main(args: Array[String]): Unit = {
        import org.apache.spark.{SparkConf, SparkContext}
        val conf = new SparkConf().setAppName("Practice").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val rdd1 =
            sc.parallelize(Array(("apple", 2), ("apple", 1), ("cat", 4), ("banana", 3), ("scala", 6), ("spark", 8)))
        val result =
            rdd1.sortBy(x => x)(Ordering.Tuple2(Ordering.String.reverse, Ordering.Int.reverse), reflect.ClassTag(classOf[(String, Int)])
        )
        println(result.collect.mkString(", "))
        sc.stop()
    }
}
