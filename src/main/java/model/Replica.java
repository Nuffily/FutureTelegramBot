package model;

public sealed class Replica permits ReplicaSimple, ReplicaRandom {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

