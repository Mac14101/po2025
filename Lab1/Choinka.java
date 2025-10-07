public class Choinka{
   public static void main(String[] args){
      int height=Integer.parseInt(args[0]);
      for(int i=1;i<=height;i++){
         for(int j=0;j<i;j++){
            System.out.print("*");
         }
         System.out.print("\n");
      }
   }
}