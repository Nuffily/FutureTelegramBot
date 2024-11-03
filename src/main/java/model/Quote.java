package model;

public sealed class Quote permits QuoteSimple, QuoteRandom {
    public String name = "";

    public Quote() {}
}

