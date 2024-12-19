package bot.console;

import model.Location;

import java.util.HashMap;
import java.util.Map;

public class SettingsService {

    private boolean repeatQuestions = true;
    private boolean repeatSolved = true;
    private boolean showAnswer = true;
    private boolean showExplanation = false;
    private final OutputService printer;
    private final Map<String, Runnable> settingMap;

    public SettingsService(OutputService printer) {
        this.printer = printer;
        settingMap = createSettingMap();
    }

    public void settingsChanger(String command) {
        if (settingMap.containsKey(command)) {
            settingMap.get(command).run();
        }
    }

    private Map<String, Runnable> createSettingMap() {
        Map<String, Runnable> map = new HashMap<>();

        map.put("repeatON", () -> {
            repeatQuestions = true;
            printer.println("встречавшиеся вопросы доступны");
        });

        map.put("repeatOFF", () -> {
            repeatQuestions = false;
            printer.println("встречавшиеся вопросы скрыты");
        });

        map.put("repeatSolvedON", () -> {
            repeatSolved = true;
            printer.println("верно решенные вопросы доступны");
        });

        map.put("repeatSolvedOFF", () -> {
            repeatSolved = false;
            printer.println("верно решенные вопросы скрыты");
        });

        map.put("showAnswerON", () -> {
            showAnswer = true;
            printer.println("при неверном решении виден ответ");
        });

        map.put("showAnswerOFF", () -> {
            showAnswer = false;
            printer.println("при неверном решении ответ не выводится");
        });

        map.put("showExplanationON", () -> {
            showExplanation = true;
            printer.println("при неверном решении выводится объяснение");
        });

        map.put("showExplanationOFF", () -> {
            showExplanation = false;
            printer.println("при неверном решении объяснения не будет");
        });

        return map;
    }

    public boolean getRepeatQuestions() {
        return repeatQuestions;
    }

    public boolean getRepeatSolved() {
        return repeatSolved;
    }

    public boolean getShowAnswer() {
        return showAnswer;
    }

    public boolean getShowExplanation() {
        return showExplanation;
    }
}


