package animals;

public class Parrot extends Animal{
    final int legs=2;
    Parrot(String name){
        this.name=name;
    }

    @Override
    public String getDescription() {
        return "Parrot " + this.name + " has " + Integer.toString(this.legs) + ".";
    }
}
