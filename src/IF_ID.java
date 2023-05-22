public class IF_ID {
    int pc;
    int fetchedInst;
    boolean lifeTime;
    int instNo;
    public String toString(){
        return "PC="+pc+" Fetched inst="+Integer.toBinaryString(fetchedInst);
    }
}
