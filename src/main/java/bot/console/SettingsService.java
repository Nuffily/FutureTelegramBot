package bot.console;

import model.Question;
import utils.MyUtils;

public class SettingsService {

    private boolean RepeatSettings = true;
    private boolean SolvedSettings = true;
    private boolean AnswerSettings = true;
    private boolean ExplanationSettings = true;

    public Question GetNotRepeatQuestion(Question[] arr, int[] questionsAttempts) {
        for (int counter = 0; counter < arr.length; counter++) {
            Question question = MyUtils.getRandomElement(arr);
            if (questionsAttempts[question.getNumber()] == 0) {
                return question;
            }
        }
        return MyUtils.getRandomElement(arr);
    }


    public boolean getRepeatSettings() {
        return RepeatSettings;
    }

    public boolean getSolvedSettings() {
        return SolvedSettings;
    }

    public boolean getAnswerSettings() {
        return AnswerSettings;
    }

    public boolean getExplanationSettings() {
        return ExplanationSettings;
    }
}


