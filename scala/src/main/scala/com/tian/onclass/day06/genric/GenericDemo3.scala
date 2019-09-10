package com.tian.onclass.day06.genric

/**
 * @author tian
 *         2019/9/10 11:07
 */
object GenericDemo3 {
    //@inline final def mapConserve[B >: A <: AnyRef](f: A => B): List[B]
    def main(args: Array[String]): Unit = {
        val dog = new Dog()
        val cat = new Cat()
        val lion = new Lion()

        // TODO 有报错
        /*new PetContainer(new Object)
        new PetContainer(new Animal)*/
        new PetContainer(new Dog) //自动多态，val dog:Pet = new Dog
        // 下界的界定意义，参考协变与逆变内容
    }
}

class Animal {
    val name: String = "animal"
}

class Pet extends Animal {
    override val name = "animal"
}

class Dog extends Pet {
    override val name: String = "dog"
}

class Cat extends Pet {
    override val name: String = "cat"
}

class Lion extends Animal {
    override val name: String = "lion"
}

class PetContainer[P <: Pet](val pet: P) //下界
