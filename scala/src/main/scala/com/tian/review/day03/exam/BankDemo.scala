package com.tian.review.day03.exam

/**
 * 扩展如下的 BankAccount类，新类 CheckingAccount
 * 对每次存款和取款都收取1美元的手续费
 *
 * @author tian
 *         2019/9/6 19:49
 */
object BankDemo {

}

class BankAccount(initialBalance: Double) {
    private var balance = initialBalance

    def deposit(amount: Double) = {
        balance += amount
        balance
    }

    def withdraw(amount: Double) = {
        balance -= amount
        balance
    }
}
class CheckingAccount(initialBalance:Double) extends BankAccount(initialBalance){
    private var balance = this.initialBalance
    override def deposit(amount: Double): Double = {
        balance += amount -1
        balance
    }

    override def withdraw(amount: Double): Double = {
        balance -= amount + 1
        balance
    }
}