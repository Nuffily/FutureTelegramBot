package console_bot;

import model.Location;

public class Bot implements Runnable{
    public InputService input = new InputService();
    private Location location = Location.MAIN;
    private final ResourceStorage storage;
    private final TestService testService;
    public final PrintService printer;
    private final TheoryService theoryService;
  
    public Bot(ResourceStorage storage) {
        this.storage = storage;
        printer = new PrintService(storage);
        testService = new TestService(printer, input);
        theoryService = new TheoryService(storage, printer);
    }


    public void run() {

        printer.println("Здарова! Введи какую-нить команду, например, help");

        while (!location.equals(Location.EXIT)) {
            String command = input.getInput();

            command = storage.getCommands().get(location).get(command);

            if (command == null) {
                printer.printlnResponse("unknownCommand");
                continue;
            }

            printer.printlnResponse(command);

            execute(command);
        }

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
                printer.println("Пока-пока!");
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