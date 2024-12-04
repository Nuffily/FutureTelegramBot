package console_bot;

import model.Location;

public class Bot implements Runnable{
    public InputService input = new InputService();
    private Location location = Location.MAIN;
    private final ResourceStorage storage;
    private final TestService testService;
    public final OutputService printer;
    private final TheoryService theoryService;
  
    public Bot(ResourceStorage storage) {
        this.storage = storage;
        printer = new OutputService(storage);
        testService = new TestService(printer, input);
        theoryService = new TheoryService(printer, input);
    }


    public void run() {

        defineButtons();
        printer.println("Здарова! Введи какую-нить команду, например, help");

        while (!location.equals(Location.EXIT)) {

            defineButtons();
            String command = input.getInput();

            command = storage.translateCommand(command, location);

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
                theoryService.startTheory();
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

    private void defineButtons() {
        switch (location) {
            case Location.MAIN:
                input.defineButtons("JavaScript", "Высшая математика", "/help");
                break;
            case Location.MATH:
            case Location.JS:
                input.defineButtons("Вопрос", "Назад", "/help");
                break;
        }
    }

    public Location getLocation() {
        return location;
    }
}