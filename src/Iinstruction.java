public class Iinstruction extends Instruction {

    private int immediate;
    private int r1;
    private int r2;

    public Iinstruction(int instruction){
        super(instruction);
        setOpcode(instruction >> 28 & 0xF);
        immediate = instruction & 0x3FFFF;
        r1 = (instruction >> 23) & 0x1F;
        r2 = (instruction >> 18) & 0x1F;
    }

    public int getImmediate(){
        return immediate;
    }

    public int getR1(){
        return r1;
    }

    public int getR2(){
        return r2;
    }


    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(spaces(19) + "opcode" + spaces(4) + "R1" + spaces(5) + "R2" + spaces(8) + "Immediate" + "\n");
        sb.append(spaces(20) + getBinaryString(getOpcode(), 4) + spaces(3) + getBinaryString(getR1(), 5) + spaces(2)
                + getBinaryString(getR2(), 5) + spaces(4) + getBinaryString(getImmediate(), 18) + "\n");
        sb.append(spaces(20) + "----------------------------------------" + "\n");
        return sb.toString();
    }

}
