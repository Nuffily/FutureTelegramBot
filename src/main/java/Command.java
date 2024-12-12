class Command {
    String location = "";
    String id = "";
    String[] inputVariants = {};

    Command() {}

    Command(String location, String id, String[] inputVariants) {
        this.location = location;
        this.id = id;
        this.inputVariants = inputVariants;
    }
}