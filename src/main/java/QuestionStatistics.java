import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;

import java.io.*;

public class QuestionStatistics {
    boolean[] questionPassed = {};
    int countOfPassedQuestions = 0;
    int[] questionsAttempts = {};
    int countOfAttemptedQuestions = 0;
    ObjectMapper objectMapper = new ObjectMapper();
    PrintService printer = new PrintService();

    QuestionStatistics() {
    }

    QuestionStatistics(Question[] questions) {
        questionPassed = new boolean[questions.length + 1];
        questionsAttempts = new int[questions.length + 1];
    }

    public void printStats() {
        printer.println("Количество пройденных вопросов: " + countOfPassedQuestions + "/" + (questionPassed.length - 1));
        printer.println("Количество встретившихся вопросов: " + countOfAttemptedQuestions + "/" + (questionPassed.length - 1));
    }

    public void updateStats(int numberOfQuestion, boolean isCorrectAnswer) {

        if (!questionPassed[numberOfQuestion] && isCorrectAnswer) {
            questionPassed[numberOfQuestion] = true;
            countOfPassedQuestions++;
        }

        if (questionsAttempts[numberOfQuestion]++ == 0)
            countOfAttemptedQuestions++;
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

            this.countOfAttemptedQuestions = newQuestionStatistics.countOfAttemptedQuestions;
            this.countOfPassedQuestions = newQuestionStatistics.countOfPassedQuestions;
            this.questionsAttempts = newQuestionStatistics.questionsAttempts;
            this.questionPassed = newQuestionStatistics.questionPassed;
        } catch (IOException e) {
            printer.println("Существующая статистика не найдена или повреждена");
        }
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
