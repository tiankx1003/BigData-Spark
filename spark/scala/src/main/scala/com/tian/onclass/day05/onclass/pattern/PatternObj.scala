package com.tian.onclass.day05.onclass.pattern

/**
 * 对象匹配
 *
 * @author tian
 *         2019/9/9 16:19
 */
/*
对象提取器
如果返回的是一个Some表示匹配成功
如果返回None表示匹配失败，继续往下匹配

2.11.1之后，unapply不是必须放回Option，可以返回其他类型的对象
但是必须有两个方法:isEmpty(表示是否匹配成功，如果为true则为空，匹配失败)
get(如果成功，使用该方法获取匹配到的值) TODO 视频
 */
object PatternObj {
    def main(args: Array[String]): Unit = {
        val r: Double = 9
        r match {
            case Sqrt(a) => println(a)
        }
    }
}

object Sqrt {
    def unapply(arg: Double) = if (arg >= 0) Some(math.sqrt(arg)) else None
}

//TODO 视频对象提取器
