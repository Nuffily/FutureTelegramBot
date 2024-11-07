import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import model.*;

import java.io.*;

public class QuestionStatistics {
    public boolean[] questionPassed = {};
    public int CountOfPassedQuestions = 0;
    public int[] questionsAttempts = {};
    public int CountOfAttemptedQuestions = 0;
    static ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    QuestionStatistics() {}

    QuestionStatistics(Question[] questions) {
        questionPassed = new boolean[questions.length + 1];
        questionsAttempts = new int[questions.length + 1];
    }

    public void printStats() {
        PrintService.println("Количество пройденных вопросов: " + CountOfPassedQuestions + "/" + (questionPassed.length - 1));
        PrintService.println("Количество встретившихся вопросов: " + CountOfAttemptedQuestions + "/" + (questionPassed.length - 1));
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

    static public QuestionStatistics uploadStats(String path) {
        File file;
        try {
            file = new File(path);
        } catch (Exception e) {
            PrintService.println("Существующая статистика не найдена или повреждена");
            return null;
        }

        try {
            QuestionStatistics questionStatistics = objectMapper.readValue(file, QuestionStatistics.class);
            PrintService.println("Статистика загружена! Чтобы взгянуть на нее, напиши 'stats'");
            return questionStatistics;
        } catch (IOException e) {
            PrintService.println("Существующая статистика не найдена или повреждена");
            return null;
        }
    }
}
