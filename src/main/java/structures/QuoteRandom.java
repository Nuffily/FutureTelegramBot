package structures;

public final class QuoteRandom extends Quote {
    public String[] responseRandom = {};

    public QuoteRandom() {}

    public QuoteRandom(String name, String[] responseRandom) {
        super(name);
        this.responseRandom = responseRandom;
    }
}
