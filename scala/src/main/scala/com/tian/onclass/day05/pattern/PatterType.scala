package com.tian.onclass.day05.pattern

/**
 * @author tian
 *         2019/9/9 15:08
 */
object PatterType {
    def main(args: Array[String]): Unit = {
        val s = List(1, 2, "a", "abc", "+", "-", false)
        //如果是数字，加一
        //如果是字母，变大写
        for (i <- s) { //匹配类型
            i match {
                case a: Int => println(a + 1)
                case "+" => println(1)
                case "-" => println(-1)
                case a => println(a)
            }
        }

        for (j <- s) {
            j match { //添加守卫
                case a: Int => println(a + 1)
                case op: String if (op == "+" || op == "-") => println(1) //添加守卫
                case a => println(a)
            }
        }
    }
}
