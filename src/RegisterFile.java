public class RegisterFile {

    public int [] registers;
    public int pc;
    public final int zeroRegister = 0;

    public RegisterFile(){
        registers = new int[32];
    }

    public void setRegister(int reg, int value){
        registers[reg] = value;
    }

    public int getRegister(int reg){
        return registers[reg];
    }

    public void setPC(int value){
        pc = value;
    }

    public int getPC(){
        return pc;
    }




}
