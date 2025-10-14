import java.util.Random;
import  java.util.ArrayList;

public class Lotto{
    static ArrayList<Integer> randomNumbers(){
        Random random=new Random();
        ArrayList<Integer> numbers=new ArrayList<Integer>();
        while (numbers.size()<6){
            int number=random.nextInt(49);
            if(!numbers.contains(number)){
                numbers.add(number);
            }
        }
        return numbers;
    }
    public static void main(String[] args){
        ArrayList<Integer> userNumbers=new ArrayList<Integer>();
        for(int i=0;i<6;i++){
            userNumbers.add(Integer.parseInt(args[i]));
        }
        long startTime=System.currentTimeMillis();
        int i=0;
        int matchedNumbers;
        do {
            matchedNumbers=0;
            i+=1;
            ArrayList<Integer> numbers = randomNumbers();
            for (int j = 0; j < 6; j++) {
                if (numbers.contains(userNumbers.get(j))) {
                    matchedNumbers += 1;
                }
            }
            System.out.print("Iteracja : ");
            System.out.print(i);
            System.out.print("\t");
            System.out.print("Wylosowane liczby : ");
            System.out.print(numbers);
            System.out.print("\n");
        }while (matchedNumbers<6);
        long endTime=System.currentTimeMillis();
        System.out.print("Twoje typy : ");
        System.out.print(userNumbers);
        System.out.print("\n");
        System.out.print("Liczba losowań :");
        System.out.print(i);
        System.out.print("\n");
        System.out.print("Czas symulacji : ");
        System.out.print((endTime-startTime)/1000);
        System.out.print(" sek");
    }
};
