package structures;

public class Quote {

    public QuoteType type;
    public String name = "";
    public String response = "";
    public String[] responseRandom = {}; //sealed class

    public Quote() {}

    public Quote(String type, String name, String response, String[] responseRandom) {
        this.type = QuoteType.valueOf(type);
        this.name = name;
        this.response = response;
        this.responseRandom = responseRandom;

    }
}
