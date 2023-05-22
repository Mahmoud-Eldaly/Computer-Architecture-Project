public class JInstruction extends Instruction {

    private int address;

    public JInstruction(int instruction) {
        super(instruction);
        setOpcode(instruction >> 28 & 0xF);
        address = instruction & 0x0FFFFFFF;
    }

    public int getAddress() {
        return address;
    }



    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(spaces(19) + "opcode" + spaces(10) + "address" + "\n");
        sb.append(spaces(20) + getBinaryString(getOpcode(), 4) + spaces(3) + getBinaryString(getAddress(), 28) + "\n");
        sb.append(spaces(20) + "----------------------------------------" + "\n");
        return sb.toString();
    }

}
