public class Quote {
    String type = "";
    String name = "";
    String output = "";
    String[] outputArray = {};

    Quote() {}

    Quote(String type, String name, String output, String[] outputArray) {
        this.type = type;
        this.name = name;
        this.output = output;
        this.outputArray = outputArray;
    }
}
