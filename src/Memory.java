public class Memory {

    public int [] memo;

    public Memory(){
        memo = new int[2048];
    }

    public void storeWord(int address, int value){
        memo[address] = value;
    }
    public void storeDataWord(int address, int value){
        memo[address+1024] = value;
    }

    public int loadWord(int address){
        return memo[address];
    }
    public int loadDataWord(int address){
        return memo[address+1024];
    }

    public String spaces(int n){
        StringBuilder spaces = new StringBuilder();
        for(int i = 0; i < n; i++){
            spaces.append(" ");
        }
        return spaces.toString();
    }


    // print the memory
    public void printMemo(){
        System.out.println(spaces(22) + "Welcome to our fantastic memory!");
        System.out.println(spaces(22) + "----------------------------------");
        for(int i = 0; i < memo.length; i++){
            System.out.println(spaces(16) + i + ": " +Integer.toBinaryString(memo[i]) );
        }
    }
}
