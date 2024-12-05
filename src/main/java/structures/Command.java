package structures;

public class Command {
    public Location location;

    public String id = "";
    public String[] inputVariants = {};

    public Command() {}


    public Command(Location location, String id, String[] inputVariants) {

        this.location = location;
        this.id = id;
        this.inputVariants = inputVariants;
    }
}