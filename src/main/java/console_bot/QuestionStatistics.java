package console_bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import model.Question;

public class QuestionStatistics {
    private boolean[] questionPassed = {};
    private int countOfPassedQuestions = 0;
    private int[] questionsAttempts = {};
    private int countOfAttemptedQuestions = 0;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private PrintService printer;

    QuestionStatistics() {
    }

    public QuestionStatistics(Question[] questions, PrintService printer) {
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
            printer.println("Существующая статистика не найдена или повреждена!");
            return;
        }

        try {
            QuestionStatistics newQuestionStatistics =
                    objectMapper.readValue(file, QuestionStatistics.class);
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
