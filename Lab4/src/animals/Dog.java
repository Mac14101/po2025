package animals;

public class Dog extends Animal{
    public Dog(String name){
        this.name=name;
        this.legs=4;
    }

    @Override
    public String getDescription() {
        return "Dog " + this.name + " has " + Integer.toString(this.legs) + " legs.";
    }

    @Override
    public void makeSound(){
        System.out.println("Dog makes sound.");
    }
}
