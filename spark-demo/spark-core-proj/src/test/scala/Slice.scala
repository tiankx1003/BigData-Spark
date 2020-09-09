/**
 * @author tian
 * @date 2019/9/20 9:26
 * @version 1.0.0
 */
object Slice {
    def main(args: Array[String]): Unit = {
        val arr1 = List(30, 50, 70, 90)
        val prePages = //arr1.slice(0, arr1.length - 1)
            arr1.take(arr1.length - 1)
        val postPages = //arr1.slice(1, arr1.length)
            arr1.tail
    }
}
