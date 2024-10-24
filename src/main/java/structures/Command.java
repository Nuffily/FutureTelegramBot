package structures;

public class Command {
    public String location = "";
    public String id = "";
    public String[] inputVariants = {};

    public Command() {}

    public Command(String location, String id, String[] inputVariants) {
        this.location = location;
        this.id = id;
        this.inputVariants = inputVariants;
    }
}