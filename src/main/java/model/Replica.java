package model;

public sealed class Replica permits ReplicaSimple, ReplicaRandom {
    private String name;

    public String getName() {
        return name;
    }
}

