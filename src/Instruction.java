public class Instruction extends Word{

    private int opcode;

    public Instruction(int instruction){
        super(instruction);
    }

    public int getOpcode(){
        return opcode;
    }

    public void setOpcode(int opcode){
        this.opcode = opcode;
    }

    // function to return spaces for formatting
    public String spaces(int n){
        String spaces = "";
        for(int i = 0; i < n; i++){
            spaces += " ";
        }
        return spaces;
    }

    public String getBinaryString(int instruction, int length) {
        String binary = Integer.toBinaryString(instruction);
        while (binary.length() < length) {
            binary = "0" + binary;
        }
        return binary;
    }

}
