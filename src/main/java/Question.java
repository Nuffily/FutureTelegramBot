public class Question {
    String body = "";
    String[] answers;
    int correctAns;
    int number;
}

class Quote {
    String type = "";
    String name = "";
    String output = "";
    String[] outputArray = {};

    Quote() {}

    Quote(String type, String name, String output, String[] outputArray) {
        this.type = type;
        this.name = name;
        this.output = output;
        this.outputArray = outputArray;
    }
}

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