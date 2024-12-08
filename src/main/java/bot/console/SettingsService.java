package bot.console;

public class SettingsService {

    private boolean repeatQuestions = true;
    private boolean repeatSolved = true;
    private boolean showAnswer = true;
    private boolean showExplanation = true;

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


