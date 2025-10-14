import java.util.Random;
import  java.util.ArrayList;

public class Lotto{
    public static void main(String[] args){
        Random random=new Random();
        ArrayList<Integer> userNumbers=new ArrayList<Integer>();
        for(int i=0;i<6;i++){
            userNumbers.add(Integer.parseInt(args[i]));
        }
        ArrayList<Integer> numbers=new ArrayList<Integer>();
        while (numbers.size()<6){
            int number=random.nextInt(49);
            if(!numbers.contains(number)){
                numbers.add(number);
            }
        }
        int matchedNumbers=0;
        for(int i=0;i<6;i++){
            if(numbers.contains(userNumbers.get(i))){
                matchedNumbers+=1;
            }
        }
        System.out.print("Twoje typy : ");
        System.out.print(userNumbers);
        System.out.print("\n");
        System.out.print("Wylosowane liczby : ");
        System.out.print(numbers);
        System.out.print("\n");
        System.out.print("Liczba trafień : ");
        System.out.print(matchedNumbers);
    }
};
