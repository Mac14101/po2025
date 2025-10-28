package animals;

public class Dog extends Animal{
    final int legs=4;
    Dog(String name){
        this.name=name;
    }

    @Override
    public String getDescription() {
        return "Dog " + this.name + " has " + Integer.toString(this.legs) + ".";
    }
}
