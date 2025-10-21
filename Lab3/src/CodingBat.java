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
}
