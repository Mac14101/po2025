package animals;

public class Snake extends Animal{
    public Snake(String name){
        this.name=name;
        this.legs=0;
    }

    @Override
    public String getDescription() {
        return "Snake " + this.name + " has " + Integer.toString(this.legs) + " legs.";
    }

    @Override
    public void makeSound(){
        System.out.println("Snake makes sound.");
    }
}
