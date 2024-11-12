package model;

public class Command {
    private Location location;
    private String id;
    private String[] inputVariants;

    public Command() {}

    public Location getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }

    public String[] getInputVariants() {
        return inputVariants;
    }
}