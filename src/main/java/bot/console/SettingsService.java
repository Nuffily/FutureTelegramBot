package bot.console;

import model.Question;
import model.State;

public class SettingsService {

    private State RepeatSettings = State.ON;
    private State SolvedSettings = State.ON;
    private State AnswerSettings = State.ON;
    private State ExplanationSettings = State.ON;


    public static Question GetNotRepeatQuestion(Question[] arr){

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


