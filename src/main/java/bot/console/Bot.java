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

    public Bot(ResourceStorage storage, OutputService outputService, InputService inputService, long chatId) {
        this.storage = storage;
        printer = outputService;
        this.input = inputService;
        settings = new SettingsService(printer);
        testService = new TestService(printer, input, storage, settings, buttons, outputService.getRandom(), chatId);
        theoryService = new TheoryService(printer, input);
        commandMap = createCommandMap();
    }


    public void run() {

        defineButtons();
        printer.println("Здарова! Введи какую-нить команду, например, help");
        testService.uploadStats(true);

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
        if (commandMap.containsKey(command)) {
            commandMap.get(command).run();
        }
    }

    private Map<String, Runnable> createCommandMap() {
        Map<String, Runnable> map = new HashMap<>();

        map.put("travelToJS", () -> location = Location.JS);

        map.put("travelToTheory", () -> {
            location = Location.THEORY;
            theoryService.startTheory();
            location = Location.MAIN;
        });

        map.put("travelToMATH", () -> location = Location.MATH);

        map.put("travelToSettings", () -> {
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

        map.put("repeatON", () -> settings.settingsChanger("repeatON"));

        map.put("repeatOFF", () -> settings.settingsChanger("repeatOFF"));

        map.put("repeatSolvedON", () -> settings.settingsChanger("repeatSolvedON"));

        map.put("repeatSolvedOFF", () -> settings.settingsChanger("repeatSolvedOFF"));

        map.put("showAnswerON", () -> settings.settingsChanger("showAnswerON"));

        map.put("showAnswerOFF", () -> settings.settingsChanger("showAnswerOFF"));

        map.put("showExplanationON", () -> settings.settingsChanger("showExplanationON"));

        map.put("showExplanationOFF", () -> settings.settingsChanger("showExplanationOFF"));

        map.put("toMenu", () -> location = Location.MAIN);

        map.put("explanationQuestion", testService::showLastExplanation);

        map.put("exit", () -> {
            location = Location.EXIT;
            printer.println("Пока-пока!");
            testService.saveStats();
        });

        map.put("startQuestion", () -> testService.questionAnswering(location));

        map.put("showStats", () -> testService.printStats(location));

        map.put("uploadStats", () -> testService.uploadStats(false));

        map.put("saveStats", testService::saveStats);

        map.put("resetStatistics", testService::resetStatistics);

        return map;
    }

    private void defineButtons() {
        switch (location) {
            case MAIN:
                buttons.set("JavaScript", "Высшая математика", "Настройки", "/help", "Пока-пока!");
                break;
            case MATH:
            case JS:
                buttons.set("Вопрос", "Назад", "Статистика", "/help");
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