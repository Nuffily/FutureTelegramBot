package bot.console;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import model.Location;
import model.Question;

public class QuestionStatistics {
    private boolean[] questionPassed = {};
    private int countOfPassedQuestions = 0;
    private int[] questionsAttempts = {};
    private int countOfAttemptedQuestions = 0;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private OutputService printer;


    public QuestionStatistics() {
    }

    public QuestionStatistics(Question[] questions, OutputService printer) {
        this.printer = printer;
        questionPassed = new boolean[questions.length + 1];
        questionsAttempts = new int[questions.length + 1];
    }

    public void printStats() {
        printer.println("Количество пройденных вопросов: "
                + countOfPassedQuestions + "/" + (questionPassed.length - 1));
        printer.println("Количество встретившихся вопросов: "
                + countOfAttemptedQuestions + "/" + (questionPassed.length - 1));
    }

    public void updateStats(int numberOfQuestion, boolean isCorrectAnswer) {

        if (!questionPassed[numberOfQuestion] && isCorrectAnswer) {
            questionPassed[numberOfQuestion] = true;
            countOfPassedQuestions++;
        }

        if (questionsAttempts[numberOfQuestion]++ == 0) {
            countOfAttemptedQuestions++;
        }
    }

    public void saveStats(String path, Location location) {

        File directory = new File(path);

        if (!directory.exists()) {
            directory.mkdir();
        }

        File file = new File(path + "Statistics" + location.toString() + ".json");

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadStats(String path) throws Exception {

        File file = new File(path);

        QuestionStatistics newQuestionStatistics =
                objectMapper.readValue(file, QuestionStatistics.class);

        this.countOfAttemptedQuestions = newQuestionStatistics.countOfAttemptedQuestions;
        this.countOfPassedQuestions = newQuestionStatistics.countOfPassedQuestions;
        this.questionsAttempts = newQuestionStatistics.questionsAttempts;
        this.questionPassed = newQuestionStatistics.questionPassed;
    }

    public void resetStatistics() {
        countOfAttemptedQuestions = 0;
        countOfPassedQuestions = 0;
        questionsAttempts = new int[questionsAttempts.length];
        questionPassed = new boolean[questionPassed.length];
    }

    public int getCountOfPassedQuestions() {
        return countOfPassedQuestions;
    }

    public boolean[] getQuestionPassed() {
        return questionPassed;
    }

    public int[] getQuestionsAttempts() {
        return questionsAttempts;
    }

    public int getCountOfAttemptedQuestions() {
        return countOfAttemptedQuestions;
    }
}
