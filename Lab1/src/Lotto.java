import java.util.Random;

public class Lotto{
   static int countNumbers(boolean[] numbers){
      int result=0;
      for(int i=0;i<numbers.length;i++){
         if(numbers[i]){
            result+=1;
         }
      }
      return result;
   }
   public static void main(String[] args){
      Random random=new Random();
      boolean[] numbers=new boolean[49];
      for(int i=0;i<numbers.length;i++){
         numbers[i]=false;
      }
      while(countNumbers(numbers)<6){
         numbers[random.nextInt(numbers.length)]=true;
      }
      for(int i=0;i<numbers.length;i++){
         if(numbers[i]){
            System.out.println(i+1);
         }
      }
   }
};
