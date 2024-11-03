package structures;

public sealed class Quote permits QuoteSimple, QuoteRandom {
    public String name = "";

    public Quote() {}

    public Quote(String name) {
        this.name = name;
    }
}

