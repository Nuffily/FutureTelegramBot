package bot.console;

import bot.telegram.TelegramButtons;
import model.Location;

import java.util.HashMap;
import java.util.Map;

public class Bot implements Runnable {
    public final InputService input;
    private Location location = Location.MAIN;
    private final ResourceStorage storage;
    private final TestService testService;
    public final OutputService printer;
    private final TheoryService theoryService;
    public SettingsService settings;
    private final TelegramButtons buttons = new TelegramButtons();
    private final Map<String, Runnable> commandMap;

    public Bot(ResourceStorage storage, OutputService outputService, InputService inputService) {
        this.storage = storage;
        printer = outputService;
        this.input = inputService;
        settings = new SettingsService(printer);
        testService = new TestService(printer, input, storage, settings, buttons, outputService.getRandom());
        theoryService = new TheoryService(printer, input);
        commandMap = createCommandMap();
    }


    public void run() {

        defineButtons();
        printer.println("Здарова! Введи какую-нить команду, например, help");

        while (!location.equals(Location.EXIT)) {

            defineButtons();
            String command = input.getInput();

            command = storage.translateCommand(command, location);

            if (command.isEmpty()) {
                printer.printlnResponse("unknownCommand");
                continue;
            }

            printer.printlnResponse(command);

            execute(command);
        }

    }

    private void execute(String command) {
        if (commandMap.containsKey(command)) { commandMap.get(command).run(); }
    }

    private Map <String, Runnable> createCommandMap(){
        Map<String, Runnable> Map = new HashMap<>();

        Map.put("travelToJS", () -> location = Location.JS);

        Map.put("travelToTheory", () -> {
            location = Location.THEORY;
            theoryService.startTheory();
            location = Location.MAIN;
        });

        Map.put("travelToMATH", () -> location = Location.MATH);

        Map.put("travelToSettings", () -> {
            location = Location.SETTINGS;
            printer.println("1. repeat (" + toONorOFF(settings.getRepeatQuestions()) + ") - выводить/скрыть "
                    + "уже встречавшиеся вопросы\n"
                    + "2. repeatSolved (" + toONorOFF(settings.getRepeatSolved()) + ") - выводить/скрыть "
                    + "верно решенные вопросы\n"
                    + "3. showAnswer (" + toONorOFF(settings.getShowAnswer()) + ") - выводить/не выводить "
                    + "ответы при неверном ответе на вопрос\n"
                    + "4. showExplanation (" + toONorOFF(settings.getShowExplanation()) + ") - выводить/не "
                    + "выводить объяснение ответа при неверном ответе на вопрос");
        });

        Map.put("repeatON", () -> settings.settingsChanger("repeatON"));

        Map.put("repeatOFF", () -> settings.settingsChanger("repeatOFF"));

        Map.put("repeatSolvedON", () -> settings.settingsChanger("repeatSolvedON"));

        Map.put("repeatSolvedOFF", () -> settings.settingsChanger("repeatSolvedOFF"));

        Map.put("showAnswerON", () -> settings.settingsChanger("showAnswerON"));

        Map.put("showAnswerOFF", () -> settings.settingsChanger("showAnswerOFF"));

        Map.put("showExplanationON", () -> settings.settingsChanger("showExplanationON"));

        Map.put("showExplanationOFF", () -> settings.settingsChanger("showExplanationOFF"));

        Map.put("toMenu", () -> location = Location.MAIN);

        Map.put("explanationQuestion", testService::showLastExplanation);

        Map.put("exit", () -> {
            location = Location.EXIT;
            printer.println("Пока-пока!");
        });

        Map.put("startQuestion", () -> testService.questionAnswering(location));

        Map.put("showStats", () -> testService.printStats(location));

        Map.put("uploadStats", () -> testService.uploadStats(location, "src/main/resources/Statistics"));

        Map.put("saveStats", () -> testService.saveStats(location, "src/main/resources/Statistics"));

        return Map;
    }

    private void defineButtons() {
        switch (location) {
            case MAIN:
                buttons.set("JavaScript", "Высшая математика", "Настройки", "/help");
                break;
            case MATH:
            case JS:
                buttons.set("Вопрос", "Назад", "/help");
                break;
            case THEORY:
                buttons.set("1", "2", "3", "back");
                break;
            case SETTINGS:
                buttons.set("repeat " + toONorOFF(!settings.getRepeatQuestions()),
                        "repeatSolved " + toONorOFF(!settings.getRepeatSolved()),
                        "showAnswer " + toONorOFF(!settings.getShowAnswer()),
                        "showExplanation " + toONorOFF(!settings.getShowExplanation()),
                        "Назад");
                break;
        }
    }

    public String[] getButtons() {
        return buttons.getButtons();
    }

    public Location getLocation() {
        return location;
    }

    public String toONorOFF(boolean bool) {
        return (bool ? "ON" : "OFF");
    }
}