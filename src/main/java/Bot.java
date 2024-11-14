import java.util.Scanner;
import model.Location;

public class Bot {

    Location location = Location.MAIN;
    ResourceStorage storage;
    Scanner scan = new Scanner(System.in);
    TestService testService;
    PrintService printer = new PrintService();

    Bot(ResourceStorage storage) {
        this.storage = storage;
        testService = new TestService(storage);
    }

    public void run() {

        String command;
        command = scan.nextLine();

        command = storage.getCommands().get(location).get(command);

        if (command == null) {
            printer.printlnResponse("unknownCommand", storage);
            return;
        }

        printer.printlnResponse(command, storage);

        execute(command);
    }

    private void execute(String command) {
        switch (command) {
            case "travelToJS":
                location = Location.JS;
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
            case "startQuestion":
                testService.questionPassion(location);
                break;
            case "showStats":
                testService.statistics.get(location).printStats();
                break;
            case "uploadStats":
                testService.statistics.get(location).uploadStats("src/main/resources/Statistics" + location.toString() + ".json");
                break;
            case "saveStats":
                testService.statistics.get(location).saveStats("src/main/resources/Statistics" + location.toString() + ".json");
                break;
            default:
        }
    }
}