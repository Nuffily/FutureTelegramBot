import com.fasterxml.jackson.databind.ObjectMapper;
import model.Location;
import model.Question;
import utils.MyUtils;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TestService {
    Map<Location, Question[]> questions;
    Map<Location, QuestionStatistics> statistics;
    ResourceStorage storage;
    Scanner scan = new Scanner(System.in);
    PrintService printer;

    TestService(ResourceStorage storage) {
        this.storage = storage;
        printer = new PrintService(storage);

        questions = new HashMap<>();
        questions.put(Location.JS, importQuestions("src/main/resources/QuestionsJS.json"));
        questions.put(Location.MATH, importQuestions("src/main/resources/QuestionsMATH.json"));

        statistics = new HashMap<>();
        statistics.put(Location.JS, new QuestionStatistics(questions.get(Location.JS)));
        statistics.put(Location.MATH, new QuestionStatistics(questions.get(Location.MATH)));
    }

    public void questionPassing(Location Location) {
        Question question = MyUtils.getRandomElement(questions.get(Location));
        question.shuffleAnswers();

        printer.println(question.getBody() + "\n-------------------------------\n"
                + "Варианты ответа:");

        for (int i = 0; i < question.getAnswers().length; i++) {
            printer.println((i + 1) + ". " + question.getAnswers()[i]);
        }

        int answer = getSuitableAnswer(question);

        if (answer == question.getCorrectAnswer()) {
            printer.printlnResponse("correctAnswer");

            statistics.get(Location).updateStats(question.getNumber(), true);
        } else {
            printer.printlnResponse("incorrectAnswer");
            printer.printResponse("correctAnswerIs");
            printer.println("" + question.getCorrectAnswer());

            statistics.get(Location).updateStats(question.getNumber(), false);
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
