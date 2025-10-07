import java.util.Random;

public class Lotto{
   public static void main(String[] args){
      Random r=new Random();
      int[] numbers={-1,-1,-1,-1,-1,-1};
      for (int i=0;i<6;i++){
         numbers[i]=r.nextInt()%50;
      }
      for(int i=0;i<6;i++){
         System.out.println(numbers[i]);
      }
   }
};
