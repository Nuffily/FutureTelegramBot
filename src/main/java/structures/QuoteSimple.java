package structures;

public final class QuoteSimple extends Quote {
    public String response = "";

    public QuoteSimple() {}

    public QuoteSimple(String name, String response) {
        super(name);
        this.response = response;
    }
}

