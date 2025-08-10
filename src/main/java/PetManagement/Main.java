package PetManagement;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hi from Pet Management System.");
        Dog dog = new Dog("Bruno", 10);
        Cat cat = new Cat("Billi", 6);
        Parrot parrot = new Parrot("Tota", 5);

        dog.sound();
        dog.doTricks();

        cat.sound();

        parrot.sound();
        parrot.talk();
    }
}

abstract class Pet{
    String name;
    int age;

    Pet(String name, int age) {
        this.name = name;
        this.age = age;
    }

    abstract void sound();
}

class Dog extends Pet {

    public Dog (String name, int age) {
        super(name, age);
    }

    @Override
    void sound() {
        System.out.println("Woof! Woof!");
    }

    void doTricks() {
        System.out.println("Dog did trick");
    }

}

class Cat extends Pet{
    public Cat(String name, int age) {
        super(name, age);
    }

    @Override
    void sound() {
        System.out.println("Mew Mew!");
    }
}

class Parrot extends Pet {
    public Parrot(String name, int age) {
        super(name, age);
    }

    @Override
    void sound() {
        System.out.println("Chirp Chirp");
    }

    void talk() {
        System.out.println("Parrot just talked");
    }
}
