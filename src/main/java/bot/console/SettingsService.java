package bot.console;

public class SettingsService {

    private boolean repeatQuestions = true;
    private boolean repeatSolved = true;
    private boolean showAnswer = true;
    private boolean showExplanation = false;
    private final OutputService printer;

    public SettingsService(OutputService printer) {
        this.printer = printer;
    }

    public void settingsChanger(String command) {
        switch (command) {
            case "repeatON":
                repeatQuestions = true;
                printer.println("встречавшиеся вопросы доступны");
                break;
            case "repeatOFF":
                repeatQuestions = false;
                printer.println("встречавшиеся вопросы скрыты");
                break;
            case "repeatSolvedON":
                repeatSolved = true;
                printer.println("верно решенные вопросы доступны");
                break;
            case "repeatSolvedOFF":
                repeatSolved = false;
                printer.println("верно решенные вопросы скрыты");
                break;
            case "showAnswerON":
                showAnswer = true;
                printer.println("при неверном решении виден ответ");
                break;
            case "showAnswerOFF":
                showAnswer = false;
                printer.println("при неверном решении ответ не выводится");
                break;
            case "showExplanationON":
                showExplanation = true;
                printer.println("при неверном решении выводится объяснение");
                break;
            case "showExplanationOFF":
                showExplanation = false;
                printer.println("при неверном решении объяснения не будет");
                break;
            default:
        }
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


