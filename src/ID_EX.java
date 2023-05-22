public class ID_EX {
    int R1val,R2val,R3val,PC;
    int R1,R2;
    int WriteRegister;
    int shamt;
    int immediate;
    int address;
    boolean lifeTime;
    int instNo;

    int MemRead,MemWrite,AlUop,Branch,RegDst,MemToReg,ALUsrc,RegWrite,jump;  //Signals of Control Unit

    public String toString(){
        return "R1="+R1+"R1val="+R1val+" R2="+R2+" R2val="+R2val+" R3val="+R3val+" PC="+PC+" WriteReg="+WriteRegister+" shamt="+shamt+" immediate="+immediate+" JAddress="+address
                +" MemRead="+MemRead+" MemWrite="+MemWrite+" AlUop="+AlUop+" Branch="+Branch+" RegDst="+RegDst+
                " MemToReg="+MemToReg+" ALUsrc="+ALUsrc+" RegWrite="+RegWrite+" jump="+jump;
    }
}
