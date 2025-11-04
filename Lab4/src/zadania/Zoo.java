package zadania;

import animals.*;
import java.util.Random;

public class Zoo {
    public static void main(String[] args) {
        Zoo zoo=new Zoo();
        Animal[] animals = new Animal[100];
        Random random = new Random();
        for (int i = 0; i < animals.length; i++) {
            int randomNumber=random.nextInt(3);
            switch (randomNumber) {
                case 0: {
                    animals[i] = new Parrot(Integer.toString(i));
                    break;
                }
                case 1: {
                    animals[i] = new Dog(Integer.toString(i));
                    break;
                }
                case 2: {
                    animals[i] = new Snake(Integer.toString(i));
                    break;
                }
            }
        }
        for (Animal animal : animals) {
            animal.makeSound();
        }
        System.out.println("Suma nóg zwierząt : " + Integer.toString(zoo.legsSum(animals)));
    }
    public int legsSum(Animal[] animals){
        int sum=0;
        for (Animal animal : animals) {
            int legs=animal.getLegs();
            sum += legs;
        }
        return sum;
    }
}
