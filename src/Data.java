public class Data extends Word{

    private int data;

    public Data(int data){
        super(data);
        this.data = data;
    }

    public int getData(){
        return data;
    }

    public void setData(int data){
        this.data = data;
    }

    @Override
    public String toString(){
        return "Data: " + data;
    }

}
