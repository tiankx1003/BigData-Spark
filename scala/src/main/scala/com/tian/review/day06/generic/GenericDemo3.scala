package com.tian.review.day06.generic

/**
 * 泛型的下界
 *
 * @author tian
 *         2019/9/10 19:02
 */
object GenericDemo3 {
    def main(args: Array[String]): Unit = {
        val animal = new Animal
        val pet = new Pet
        val lion = new Lion
        val cat = new Cat
        val dog = new Dog
        val petC = new PetContainer(new Animal)
        println("Hello World")
    }

    def print(petContainer: PetContainer[Pet]): Unit =
        println(petContainer.pet.name)
}

class Animal {
    val name: String = "animal"
}

class Pet extends Animal {
    override val name: String = "pet"
}

class Lion extends Animal {
    override val name: String = "lion"
}

class Cat extends Pet {
    override val name: String = "pet"
}

class Dog extends Pet {
    override val name: String = "dog"
}

/**
 * 泛型的下界
 *
 * @param pet
 * @tparam T 泛型下界为Pet
 */
class PetContainer[T >: Pet](val pet: T)