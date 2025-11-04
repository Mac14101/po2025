package animals;

public abstract class Animal {
    String name;
    int legs;
    public abstract String getDescription();
    public int getLegs(){
        return this.legs;
    };
}
