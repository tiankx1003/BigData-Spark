package com.tian.review.day03.exam

/**
 * 编写一个扑克牌只能有 4 种花色,让其 toString 方法分别返回♣,♦,♥,♠，
 * 并实现一个函数,检查某张牌的花色是否为红色
 *
 * @author tian
 *         2019/9/6 19:34
 */
object CardDemo {
    def main(args: Array[String]): Unit = {
        val card1 = new Card("Club")
        val card2 = new Card("Heart")
        println(card1.toString)
        println(card1.isRed)
        println(card2)
        println(card2.isRed)
    }
}

abstract class Color {
    def isRed: Boolean
}

class Card(val shape: String) extends Color {
    override def isRed: Boolean = {
        if (shape == "Heart" || shape == "Diamond") true
        //        else if(shape == "Spade" || shape == "Club") false
        else false
    }

    override def toString: String = {
        if (shape == "Heart") "♥"
        else if (shape == "Spade") "♠"
        else if (shape == "Diamond") "♦"
        else if (shape == "Club") "♣"
        else "error"
    }
}
