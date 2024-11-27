import java.util.Scanner;
import model.Location;

public class Bot {
    public InputService input = new InputService();
    private Location location = Location.MAIN;
    private final ResourceStorage storage;
    private final Scanner scan = new Scanner(System.in);
    private final TestService testService;
    private final PrintService printer;
    private final TheoryService theoryService;
  
    Bot(ResourceStorage storage) {
        this.storage = storage;
        testService = new TestService(storage, input);
        printer = new PrintService(storage);
        theoryService = new TheoryService(storage);
    }


    public void run() {

        String command = input.getInput();

        command = storage.getCommands().get(location).get(command);

        if (command == null) {
            printer.printlnResponse("unknownCommand");
            return;
        }

        printer.printlnResponse(command);

        execute(command);
    }

    private void execute(String command) {
        switch (command) {
            case "travelToJS":
                location = Location.JS;
                break;
            case "travelToTheory":
                location = Location.THEORY;
                break;
            case "travelToMATH":
                location = Location.MATH;
                break;
            case "toMenu":
                location = Location.MAIN;
                break;
            case "exit":
                location = Location.EXIT;
                break;
            case "startTheory":
                theoryService.startTheory();
                break;
            case "startQuestion":
                testService.questionAnswering(location);
                break;
            case "showStats":
                testService.printStats(location);
                break;
            case "uploadStats":
                testService.uploadStats(location, "src/main/resources/Statistics");
                break;
            case "saveStats":
                testService.saveStats(location, "src/main/resources/Statistics");
                break;
            default:
        }
    }

    public Location getLocation() {
        return location;
    }
}