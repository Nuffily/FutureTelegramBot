package structures;

public class Quote {
    public  String type = "";
    public String name = "";
    public String output = "";
    public String[] outputArray = {}; //sealed class

    public Quote() {}

    public Quote(String type, String name, String output, String[] outputArray) {
        this.type = type;
        this.name = name;
        this.output = output;
        this.outputArray = outputArray;
    }
}
