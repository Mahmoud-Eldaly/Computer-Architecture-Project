import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Engine {

    public static HashMap<String, Integer> opcodes;
    public static HashMap<String, Character> types;
    public static HashMap<Integer, String> operations;
    public static Memory memory;
    public static RegisterFile registerFile;
    public static int mp; // memory pointer
    public static int size; // program size
    public static IF_ID if_id;
    public static ID_EX id_ex;
    public static EX_M ex_m;
    public static M_WB m_wb;
    public static int time;


    public Engine(){
        init();
    }

    public static void init() {
        opcodes = new HashMap<>();
        types = new HashMap<>();
        operations = new HashMap<>();
        memory = new Memory();
        registerFile = new RegisterFile();
        time=0;

        // opcodes
        {
            // R-Type
            opcodes.put("ADD", 0);
            opcodes.put("SUB", 1);
            opcodes.put("SLL", 8);
            opcodes.put("SRL", 9);
            // I-Type
            opcodes.put("MULI", 2);
            opcodes.put("ADDI", 3);
            opcodes.put("BNE", 4);
            opcodes.put("ANDI", 5);
            opcodes.put("ORI", 6);
            opcodes.put("LW", 10);
            opcodes.put("SW", 11);
            // J-Type
            opcodes.put("J", 7);
        }


        // operations
        {
            // R-Type
            operations.put(0, "ADD");
            operations.put(1, "SUB");
            operations.put(8, "SLL");
            operations.put(9, "SRL");

            // I-Type
            operations.put(2, "MULI");
            operations.put(3, "ADDI");
            operations.put(4, "BNE");
            operations.put(5, "ANDI");
            operations.put(6, "ORI");
            operations.put(10, "LW");
            operations.put(11, "SW");

            // J-Type
            operations.put(7, "J");

        }

        // types
        {
            // R-type
            types.put("ADD", 'R');
            types.put("SUB", 'R');
            types.put("SLL", 'R');
            types.put("SRL", 'R');

            // I-type
            types.put("MULI", 'I');
            types.put("ADDI", 'I');
            types.put("BNE", 'I');
            types.put("ANDI", 'I');
            types.put("ORI", 'I');
            types.put("LW", 'I');
            types.put("SW", 'I');

            // J-type
            types.put("J", 'J');

        }
    }

    public void parseLine(String line) throws Exception {
        StringTokenizer st = new StringTokenizer(line);
        String inst=st.nextToken();
        if(inst.equals("J"))
            encode(inst,st.nextToken(),"","");
        else
            encode(inst, st.nextToken(), st.nextToken(), st.nextToken());
        if (st.hasMoreTokens()) {
            System.out.println(line);
            throw new Exception("Invalid instruction");
        }
    }

    public void parseFile(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scan = new Scanner(file);
//        System.out.println(path);
        try{
            while(scan.hasNextLine()){
                String line = scan.nextLine();
//                System.out.println(line);
                parseLine(line);
                mp++;
                size++;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error: Parse error");
        }

    }

    public void encode(String operation, String oprand1, String oprand2, String oprand3){
        int value;
//        System.out.println(operation+" "+oprand1+" "+oprand2+" "+oprand3);
        if (types.get(operation) == 'R') {
            int opcode = opcodes.get(operation);
            int r1 = Integer.parseInt(oprand1.substring(1)) ;
            int r2 = Integer.parseInt(oprand2.substring(1)) ;

            if(opcode == 0 || opcode == 1) {
                int r3 = Integer.parseInt(oprand3.substring(1)) ;
                value = (opcode << 28) | (r2 << 23) | (r3 << 18) | (r1 << 13);
            }
            else
                value = (opcode << 28) | (r2 << 23) | (r1 << 13) | Integer.parseInt(oprand3);
            memory.storeWord(mp, value);
        }
        else if (types.get(operation) == 'I') {
            int opcode = opcodes.get(operation);
            int r1 = Integer.parseInt(oprand1.substring(1)) ;
            int r2 = Integer.parseInt(oprand2.substring(1)) ;
            int immediate = Integer.parseInt(oprand3)&0x3ffff;
            value = (opcode << 28) | (r2 << 23) | (r1 << 18) | immediate;
            memory.storeWord(mp, value);
        }
        else {
            int opcode = opcodes.get(operation);
            int address = Integer.parseInt(oprand1);
            value = (opcode << 28) | address;
            memory.storeWord(mp, value);
        }
//        System.out.println("value="+value);
    }

    public void fetch()  {
        System.out.println("Fetching Instruction "+if_id.instNo);
        System.out.println("Entering Fetch: PC="+registerFile.pc);
       if_id.fetchedInst = memory.memo[registerFile.pc];
       if_id.instNo=registerFile.getPC()+1;
        registerFile.setPC(registerFile.getPC() + 1);
        if_id.pc = registerFile.getPC();

        System.out.println("Out of Fetching:"+if_id.toString());
    }
    public void decode() {
//        System.out.println("just fetched "+Integer.toBinaryString(if_id.fetchedInst));
        if(time2%2==0&& if_id.lifeTime){
            if_id.lifeTime=false;
            System.out.println("No Decoding Now!!");
            // even true
            return;
        }
        if(time2%2==1&& !if_id.lifeTime){
            if_id.lifeTime=false;
            System.out.println("No Decoding Now!!");
            // Odd false
            return;
        }
        System.out.println("Decoding Instruction "+id_ex.instNo);
        System.out.println("Entering Decoding: "+if_id.toString());
        if(!if_id.lifeTime){
            id_ex.instNo=if_id.instNo;
            if_id.lifeTime=true;
//            System.out.println(time2+" "+if_id.lifeTime);
//            System.out.println("Decoding Instruction "+id_ex.instNo);
            return;
        }
        int value=if_id.fetchedInst;
        int opcode = (value >>> 28);
//        System.out.println("opo"+opcode);
        int R1=(value>>23)&0x1f;
        int R2=(value>>18)&0x1f;
        int R3=(value>>13)&0x1f;

        id_ex.PC = if_id.pc;
        id_ex.address = value&0x0fffffff;
        id_ex.R1val = registerFile.getRegister(R1);
        id_ex.R2val = registerFile.getRegister(R2);
        id_ex.R3val = registerFile.getRegister(R3);
        id_ex.R1=R1;
        id_ex.R2=R2;
        int tmp = value & 0x3ffff;
        if((tmp & (1<<17)) != 0)
            tmp |= 0b11111111111111000000000000000000;
        id_ex.immediate = tmp;
        id_ex.shamt = value&0x1fff;
        id_ex.AlUop = opcode;
        id_ex.MemRead = (opcode == 10)? 1 : 0;
        id_ex.MemWrite = (opcode == 11)? 1 : 0;
        id_ex.Branch = (opcode == 4)? 1 : 0;
        id_ex.RegDst = (opcode <= 9)? 1 : 0;
        id_ex.ALUsrc = (opcode < 2)? 0 : ((opcode == 8 || opcode == 9)? 1 : 2);
        id_ex.MemToReg = id_ex.MemRead;
        id_ex.RegWrite = (opcode != 4 && opcode != 11 && opcode != 7)? 1 : 0;
        id_ex.WriteRegister = (opcode < 2 || opcode == 8 || opcode == 9)? R3 : R2;
        id_ex.jump = (opcode == 7)? 1 : 0;
        if_id.lifeTime=false;

//        System.out.println("j from decode: "+ id_ex.jump);
//        System.out.println("opcode for deco: "+opcode);
        System.out.println("Out of Decode:"+id_ex.toString());

    }

    public void execute(){

        if(time2%2==1&& !id_ex.lifeTime){
            System.out.println("No Exec Now!! ");
            return;
        }
        System.out.println("Executing Instruction "+ex_m.instNo);
        System.out.println("into EXEC"+id_ex.toString());
        if(!id_ex.lifeTime){
            ex_m.instNo=id_ex.instNo;
            id_ex.lifeTime=true;
//            System.out.println("Executing Instruction "+ex_m.instNo);
            return;
        }
        int result = 0;
        int op1 = id_ex.R1val;
        if(m_wb.WriteRegister==id_ex.R1&&id_ex.R1!=0&&m_wb.RegWrite==1){
//            System.out.println("Hazard1");
            if(m_wb.MemRead==1)
                op1=m_wb.readData;
            else
                op1=m_wb.ALU_result;
//            System.out.println("op1H1="+op1);

        }
        if(ex_m.WriteRegister==id_ex.R1&&id_ex.R1!=0&&ex_m.RegWrite==1&&id_ex.AlUop!=4&&id_ex.AlUop!=7&&id_ex.AlUop!=10&&id_ex.AlUop!=11){
//            System.out.println("Hazard2");
            op1=ex_m.ALU_result;
//            System.out.println("op1H2="+op1);
        }
        int op2 = 0;
        switch (id_ex.ALUsrc){
            case 0:op2 = id_ex.R2val;break;
            case 1:op2 = id_ex.shamt;break;
            case 2:op2 = id_ex.immediate;break;
        }
        if(id_ex.ALUsrc==0&&m_wb.WriteRegister==id_ex.R2&&id_ex.R2!=0){
            op2=m_wb.readData;
        }
        if(id_ex.ALUsrc==0&&ex_m.WriteRegister==id_ex.R2&&id_ex.R2!=0){
            op2=ex_m.ALU_result;
        }

        switch (id_ex.AlUop) {
            case 0 : result = op1 + op2;break;// ADD
            case 1 : result = op1 - op2;break;// SUB
            case 2 : result = op1 * op2;break;// MULI
            case 3 : result = op1 + op2;break;// ADDI
            case 4 : result = id_ex.R1val-id_ex.R2val;break; // BNQ
            case 5 : result = op1 & op2;break;// ADDI
            case 6 : result = op1 | op2;break; // ORI
            case 7 : break;// Jump
            case 8 : result = op1 << op2;break; // SLL
            case 9 : result = op1 >>> op2;break; // SRL
            case 10 : result = op1 + op2;break;// LW
            case 11 : result = op1 + op2;break;// SW
            default : System.out.println("Unknown instruction");
        }
//        System.out.println("result="+result+" RW="+id_ex.RegWrite);
        ex_m.ALU_result = result;
        ex_m.ZeroFlag = (result == 0)? 1 : 0;
        ex_m.Branch = id_ex.Branch;
        ex_m.MemWrite = id_ex.MemWrite;
        ex_m.MemRead = id_ex.MemRead;
        ex_m.MemToReg = id_ex.MemToReg;
        ex_m.WriteData = id_ex.R2val; //WriteData is the Data that may be written in Memory
        ex_m.PC = id_ex.PC;
        ex_m.AddedPC = id_ex.PC+id_ex.immediate;
        ex_m.jumpAddress = ((id_ex.PC - 1) & 0xf0000000) + id_ex.address;
        ex_m.RegWrite = id_ex.RegWrite;
        ex_m.RegDst = id_ex.RegDst;
        ex_m.WriteRegister = id_ex.WriteRegister;
        ex_m.jump = id_ex.jump;
        id_ex.lifeTime=false;
//        System.out.println("jjj="+ ex_m.jump);
        if(ex_m.jump == 1) {
//            time2=1;
//            System.out.println("JJJJJ");
//            registerFile.setPC(ex_m.jumpAddress);
            targetAdd=ex_m.jumpAddress;
            Goflush=true;
        }
        else if(ex_m.Branch == 1 && ex_m.ZeroFlag == 0) {
//            time2=1;
            System.out.println("Branching.. to :"+ex_m.AddedPC);
            targetAdd=ex_m.AddedPC;
            Goflush=true;
        }




    }
    public void Mem(){

        m_wb.instNo=ex_m.instNo;
        m_wb.MemRead=ex_m.MemRead;
        if(ex_m.MemRead == 1)
            m_wb.readData = memory.loadDataWord(ex_m.ALU_result);
        else if(ex_m.MemWrite == 1) {
            memory.storeDataWord(ex_m.ALU_result, ex_m.WriteData);
            memory.printMemo();
        }
        m_wb.ALU_result = ex_m.ALU_result;
        m_wb.MemToReg = ex_m.MemToReg;
        m_wb.RegWrite = ex_m.RegWrite;
        m_wb.RegDst = ex_m.RegDst;
        m_wb.WriteRegister = ex_m.WriteRegister;
        System.out.println("Memoing Instruction "+m_wb.instNo);



    }
    public void WriteBack(){
        System.out.println("Writing Back Instruction "+m_wb.instNo);
        if(m_wb.RegWrite == 1){
            System.out.println("changing reg="+m_wb.WriteRegister+" value="+ m_wb.ALU_result);
            registerFile.setRegister(m_wb.WriteRegister,m_wb.WriteRegister == 0? 0 : m_wb.MemToReg == 1? m_wb.readData : m_wb.ALU_result);
        }




    }
    public static void  flush(){
        System.out.println("Flushingg...");
        Goflush=false;
        if_id=new IF_ID();
        id_ex=new ID_EX();
        registerFile.setPC(targetAdd);
//        ex_m=new EX_M();
//        m_wb=new M_WB();
    }
    static  int targetAdd=0;
    static int time2=1;
    static boolean Goflush=false;
    public static void main(String[] args) throws FileNotFoundException {
        Engine e = new Engine();
        if_id=new IF_ID();
        id_ex=new ID_EX();
        ex_m=new EX_M();
        m_wb=new M_WB();
        e.parseFile("Prog.txt"); // <<- this is my path to the file put yours here
        int time=1;
//        registerFile.setRegister(1,5);

        int limit =6;
        while(limit>0&&registerFile.getPC()<mp+7) {
            System.out.println("Start of Time "+time+"------------------------------------------");
            System.out.println("initially, pc="+registerFile.pc);
            if(time2%2==1&&time>=7)
                e.WriteBack();
            else
                System.out.println("No Write Back Now");
//            if(flushed){
//                System.out.println("rf="+Arrays.toString(registerFile.registers)+" pc="+registerFile.pc);
//                System.out.println("End of Time "+time+"------------------------------------------");
//                time++;
////                time2++;
//                flushed=false;
//                continue;
//            }
            if(time2%2==0&&time>5)
                e.Mem();
            else
                System.out.println("No Memoing Now");
            if(time2<2*mp+4&&time>3)
                e.execute();
            else
                System.out.println("No Executing Now");
            if(time2<2*mp+2&&time>1)
                e.decode();
            else
                System.out.println("No Decoding Now");
            if(registerFile.getPC()>=mp) {
                boolean tmp=if_id.lifeTime;
                if(limit<6)
                    if_id=new IF_ID();
                if_id.lifeTime=tmp;
                limit--;
                System.out.println("Code Finished");
            }
            else if(time2%2==1&&time2<2*mp) {
                e.fetch();
                limit=6;
            }
            else {
                System.out.println("No Fetching Now");
                limit=6;
            }
            System.out.println("rf="+Arrays.toString(registerFile.registers)+" pc="+registerFile.pc);
//            System.out.println("Time2="+time2);
            if(Goflush){
//                System.out.println("Flushing...");
                time2=-1;
                flush();
            }
            System.out.println("End of Time "+time+"------------------------------------------");

//            memory.printMemo();







            time++;
            time2++;
        }
        System.out.println(limit+" "+registerFile.getPC()+" "+(mp+5));

        System.out.println(mp);

        memory.printMemo();


    }



}
