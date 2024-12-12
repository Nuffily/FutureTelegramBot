package bot.console;

import model.Location;

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
        printer = new OutputService(storage);
        settings = new SettingsService(printer);
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

            if (command.isEmpty()) {
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
                theoryService.startTheory();
                location = Location.MAIN;
                break;
            case "travelToMATH":
                location = Location.MATH;
                break;
            case "travelToSettings":
                location = Location.SETTINGS;
                printer.println("1. repeat (" + toONorOFF(settings.getRepeatQuestions()) + ") - выводить/скрыть "
                        + "уже встречавшиеся вопросы\n"
                        + "2. repeatSolved (" + toONorOFF(settings.getRepeatSolved()) + ") - выводить/скрыть "
                        + "верно решенные вопросы\n"
                        + "3. showAnswer (" + toONorOFF(settings.getShowAnswer()) + ") - выводить/не выводить "
                        + "ответы при неверном ответе на вопрос\n"
                        + "4. showExplanation (" + toONorOFF(settings.getShowExplanation()) + ") - выводить/не "
                        + "выводить объяснение ответа при неверном ответе на вопрос");
                break;
            case "repeatON", "repeatOFF", "repeatSolvedON",
                 "repeatSolvedOFF", "showAnswerON", "showAnswerOFF",
                 "showExplanationON", "showExplanationOFF":
                settings.settingsChanger(command);
                break;
            case "toMenu":
                location = Location.MAIN;
                break;
            case "explanationQuestion":
                testService.showLastExplanation();
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
                input.defineButtons("JavaScript", "Высшая математика", "Настройки", "/help");
                break;
            case MATH:
            case JS:
                input.defineButtons("Вопрос", "Назад", "/help");
                break;
            case THEORY:
                input.defineButtons("1", "2", "3", "back");
                break;
            case SETTINGS:
                input.defineButtons("repeat " + toONorOFF(!settings.getRepeatQuestions()),
                        "repeatSolved " + toONorOFF(!settings.getRepeatSolved()),
                        "showAnswer " + toONorOFF(!settings.getShowAnswer()),
                        "showExplanation " + toONorOFF(!settings.getShowExplanation()),
                        "Назад");
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

    public String toONorOFF(boolean bool) {
        return (bool ? "ON" : "OFF");
    }
}