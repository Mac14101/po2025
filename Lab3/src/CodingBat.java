public class CodingBat {
    // Warmup1 > sleepIn
    public boolean sleepIn(boolean weekday, boolean vacation) {
        if(!weekday){
            return true;
        }
        return vacation;
    }
    // Warmup1 > notString
    public String notString(String str){
        if(str.split(" ")[0].equals("not")){
            return str;
        }else{
            return "not "+str;
        }
    }
    // Array2 > shiftLeft
    public int[] shiftLeft(int[] nums) {
        if(nums.length==0){
            return nums;
        }
        int buff=nums[0];
        for(int i=0;i<nums.length-1;i++){
            nums[i]=nums[i+1];
        }
        nums[nums.length-1]=buff;
        return nums;
    }

}
