package animals;

public abstract class Animal {
    String name;
    int legs;
    public abstract String getDescription();
    public int getLegs(){
        return this.legs;
    };
    public void makeSound(){
        System.out.println("Animal makes sound.");
    }
}
