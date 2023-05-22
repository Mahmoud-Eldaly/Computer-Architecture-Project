public class RInstruction extends Instruction {


    private int R1;
    private int R2;
    private int R3;
    private int shamt;

    public RInstruction(int instruction) {
        super(instruction);
        setOpcode(instruction >> 28 & 0xF);
        R1 = (instruction >> 23) & 0x1F;
        R2 = (instruction >> 18) & 0x1F;
        R3 = (instruction >> 13) & 0x1F;
        shamt = instruction & 0x1FFF;
    }

    public int getR1(){
        return R1;
    }

    public int getR2(){
        return R2;
    }

    public int getR3(){
        return R3;
    }

    public int getShamt(){
        return shamt;
    }



    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(spaces(19) + "opcode" + spaces(3) + "R1" + spaces(6) + "R2" + spaces(4) + "R3" + spaces(9) + "shamt" + "\n");
        sb.append(spaces(20) + getBinaryString(getOpcode(), 4) + spaces(3) + getBinaryString(getR1(), 5) + spaces(2)
                + getBinaryString(getR2(), 5) + spaces(2) + getBinaryString(getR3(), 5) +
                spaces(4) + getBinaryString(getShamt(), 13) + "\n");
        sb.append(spaces(20) + "----------------------------------------" + "\n");
        return sb.toString();
    }




}
