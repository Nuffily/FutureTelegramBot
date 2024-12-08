package bot.console;

import model.Question;
import model.State;
import utils.MyUtils;

public class SettingsService {

    private State RepeatSettings = State.ON;
    private State SolvedSettings = State.ON;
    private State AnswerSettings = State.ON;
    private State ExplanationSettings = State.ON;


    public Question GetNotRepeatQuestion(Question[] arr, int[] questionsAttempts){
        for(int counter = 0; counter < arr.length; counter++){
            Question question = MyUtils.getRandomElement(arr);
            if (questionsAttempts[question.getNumber()] == 0){
                return question;
            }
        }
        return MyUtils.getRandomElement(arr);
    }


    public State getRepeatSettings() {
        return RepeatSettings;
    }

    public State getSolvedSettings() {
        return SolvedSettings;
    }

    public State getAnswerSettings() {
        return AnswerSettings;
    }

    public State getExplanationSettings() {
        return ExplanationSettings;
    }
}


