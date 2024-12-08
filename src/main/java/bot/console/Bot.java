package bot.console;

import model.Location;
import model.State;

public class Bot implements Runnable {
    public InputService input = new InputService();
    private Location location = Location.MAIN;
    private final ResourceStorage storage;
    private final TestService testService;
    public final OutputService printer;
    private final TheoryService theoryService;
    public SettingsService settings;

    public Bot(ResourceStorage storage) {
        this.storage = storage;
        settings = new SettingsService();
        printer = new OutputService(storage);
        testService = new TestService(printer, input, storage, settings);
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
            case "travelToSettings":
                location = Location.SETTINGS;
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
            case MAIN:
                input.defineButtons("JavaScript", "Высшая математика", "/help");
                break;
            case MATH:
            case JS:
                input.defineButtons("Вопрос", "Назад", "/help");
                break;
        }
    }

    public Location getLocation() {
        return location;
    }

    public void consoleModeDisable() {
        printer.consoleMode = false;
        input.consoleMode = false;
    }
}