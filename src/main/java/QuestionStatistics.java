import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import model.*;

import java.io.*;

public class QuestionStatistics {
    public boolean[] questionPassed = {};
    public int CountOfPassedQuestions = 0;
    public int[] questionsAttempts = {};
    public int CountOfAttemptedQuestions = 0;
    ObjectMapper objectMapper = new ObjectMapper();
    PrintService printer = new PrintService();


    QuestionStatistics() {}

    QuestionStatistics(Question[] questions) {
        questionPassed = new boolean[questions.length + 1];
        questionsAttempts = new int[questions.length + 1];
    }

    public void printStats() {
        printer.println("Количество пройденных вопросов: " + CountOfPassedQuestions + "/" + (questionPassed.length - 1));
        printer.println("Количество встретившихся вопросов: " + CountOfAttemptedQuestions + "/" + (questionPassed.length - 1));
    }

    public void updateStats(int numberOfQuestion, boolean result) {

        if (!questionPassed[numberOfQuestion] && result) {
            questionPassed[numberOfQuestion] = true;
            CountOfPassedQuestions++;
        }

        if (questionsAttempts[numberOfQuestion]++ == 0)
            CountOfAttemptedQuestions++;
    }

    public void saveStats(String path) {

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadStats(String path) {
        File file;
        try {
            file = new File(path);
        } catch (Exception e) {
            printer.println("Существующая статистика не найдена или повреждена");
            return;
        }

        try {
            QuestionStatistics newQuestionStatistics = objectMapper.readValue(file, QuestionStatistics.class);
            printer.println("Статистика загружена! Чтобы взгянуть на нее, напиши 'stats'");
            this.CountOfAttemptedQuestions = newQuestionStatistics.CountOfAttemptedQuestions;
            this.CountOfPassedQuestions = newQuestionStatistics.CountOfPassedQuestions;
            this.questionsAttempts = newQuestionStatistics.questionsAttempts;
            this.questionPassed = newQuestionStatistics.questionPassed;
        } catch (IOException e) {
            printer.println("Существующая статистика не найдена или повреждена");
        }
    }
}
