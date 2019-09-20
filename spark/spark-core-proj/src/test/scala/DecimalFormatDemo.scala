import java.text.DecimalFormat

/**
 * @author tian
 * @date 2019/9/20 10:24
 * @version 1.0.0
 */
object DecimalFormatDemo {
    def main(args: Array[String]): Unit = {
        val formatter = new DecimalFormat(".00")
        println(formatter.format(10.111111))
        val formatter2 = new DecimalFormat(".00%")
        println(formatter2.format(10.111111))

    }
}
