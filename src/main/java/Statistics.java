import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import structures.*;
import java.io.*;

public class Statistics {
    public boolean[] questionPassed = {};
    public int CountOfPassedQuestions = 0;
    public int[] questionsAttempts = {};
    public int CountOfAttemptedQuestions = 0;

    Statistics() {}

    Statistics(boolean[] questionPassed, int CountOfPassedQuestions,
                int[] questionsAttempts, int CountOfAttemptedQuestions) {
        this.questionPassed = questionPassed;
        this.CountOfPassedQuestions = CountOfPassedQuestions;
        this.questionsAttempts = questionsAttempts;
        this.CountOfAttemptedQuestions = CountOfAttemptedQuestions;
    }

    public void printStats() {
        PrintService.println("Количество пройденных вопросов: " + CountOfPassedQuestions + "/" + questionPassed.length);
        PrintService.println("Количество встретившихся вопросов: " + CountOfAttemptedQuestions + "/" + questionPassed.length);
    }

    Statistics(ResourceStorage storage, Question[] questions) {
        questionPassed = new boolean[questions.length + 1];
        questionsAttempts = new int[questions.length + 1];
    }

    public void update(int numberOfQuestion, boolean result) {

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
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(new File(path), Statistics.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
