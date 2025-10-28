package animals;

public class Snake extends Animal{
    final int legs=0;
    Snake(String name){
        this.name=name;
    }

    @Override
    public String getDescription() {
        return "Snake " + this.name + " has " + Integer.toString(this.legs) + ".";
    }
}
