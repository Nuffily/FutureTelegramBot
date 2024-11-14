package model;

public sealed class Quote permits QuoteSimple, QuoteRandom {
    private String name;

    public Quote() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

