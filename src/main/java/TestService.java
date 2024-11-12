import com.fasterxml.jackson.databind.ObjectMapper;
import model.Location;
import model.Question;
import utils.MyUtils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TestService {
    Question[] questions;
    ResourceStorage storage;
    QuestionStatistics statistics;
    String StatPath;
    Scanner scan = new Scanner(System.in);
    PrintService printer = new PrintService();

    TestService(ResourceStorage storage, Location location, String StatPath) {
        this.storage = storage;
        if (location.equals(Location.JS))
            questions = importQuestions("src/main/resources/QuestionsJS.json");
        else
            questions = importQuestions("src/main/resources/QuestionsMATH.json");
        statistics = new QuestionStatistics(questions);
        this.StatPath = StatPath;
    }

    public void questionPassion() {

        Question question = MyUtils.getRandomElement(questions);

        printer.println(question.getBody() + "\n-------------------------------\n"
                + "Варианты ответа:");

        String rightAnswer = question.getAnswers()[question.getCorrectAnswer() - 1];

        question.shuffleAnswers();


        for (int i = 0; i < question.getAnswers().length; i++) {
            printer.println((i + 1) + ". " + question.getAnswers()[i]);
        }

        int answer = getSuitableAnswer(question);

        if (answer == question.getCorrectAnswer()) {
            printer.printlnResponse("correctAnswer", storage);

            statistics.updateStats(question.getNumber(), true);
        }
        else {
            printer.printlnResponse("incorrectAnswer", storage);
            printer.printResponse("correctAnswerIs", storage);
            printer.println("" + question.getCorrectAnswer());

            statistics.updateStats(question.getNumber(), false);
        }
    }

    private int getSuitableAnswer(Question question) {
        String ans;
        int answer;

        while (true) {
            ans = scan.nextLine();

            if (!ans.matches("[-+]?\\d+")) {
                printer.println("Ответ должен быть числом");
                continue;
            }

            answer = Integer.parseInt(ans);

            if (answer < 1 || answer > question.getAnswers().length) {
                printer.println("Такого варианта ответа нет");
                continue;
            }

            return answer;
        }
    }

    private Question[] importQuestions(String path) {
        return importFromJson(path, Question[].class);
    }

    public <T> T[] importFromJson(String path, Class<T[]> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(path), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
