import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import model.*;
import java.io.*;

public class Statistics {
    public boolean[] questionPassed = {};
    public int CountOfPassedQuestions = 0;
    public int[] questionsAttempts = {};
    public int CountOfAttemptedQuestions = 0;
    static ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    Statistics() {}

    Statistics(Question[] questions) {
        questionPassed = new boolean[questions.length + 1];
        questionsAttempts = new int[questions.length + 1];
    }

    public void printStats() {
        PrintService.println("Количество пройденных вопросов: " + CountOfPassedQuestions + "/" + questionPassed.length);
        PrintService.println("Количество встретившихся вопросов: " + CountOfAttemptedQuestions + "/" + questionPassed.length);
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
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static public Statistics uploadStats(String path) {
        try {
            return objectMapper.readValue(new File(path), Statistics.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
