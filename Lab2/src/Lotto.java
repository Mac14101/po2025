import java.util.Random;
import  java.util.ArrayList;

public class Lotto{
    public static void main(String[] args){
        Random random=new Random();
        ArrayList<Integer> numbers=new ArrayList<Integer>();
        while (numbers.size()<6){
            int number=random.nextInt(49);
            if(!numbers.contains(number)){
                numbers.add(number);
            }
        }
        System.out.print("Wylosowane liczby : ");
        System.out.print(numbers);
    }
};
