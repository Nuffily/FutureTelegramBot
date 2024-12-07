package bot.console;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.Location;
import model.Question;
import utils.MyUtils;

public class TestService {
    private final Map<Location, Question[]> questions;
    private final Map<Location, QuestionStatistics> statistics;
    private final OutputService printer;
    private final InputService input;

    public TestService(OutputService printer, InputService inputService) {
        this.printer = printer;
        this.input = inputService;

        questions = new HashMap<>();
        questions.put(Location.JS, importQuestions("src/main/resources/QuestionsJS.json"));
        questions.put(Location.MATH, importQuestions("src/main/resources/QuestionsMATH.json"));

        statistics = new HashMap<>();
        statistics.put(Location.JS, new QuestionStatistics(questions.get(Location.JS), printer));
        statistics.put(Location.MATH, new QuestionStatistics(questions.get(Location.MATH), printer));
    }

    public void printStats(Location location) {
        statistics.get(location).printStats();
    }

    public void uploadStats(Location location, String path) {
        statistics.get(location).uploadStats(path + location.toString() + ".json");
    }

    public void saveStats(Location location, String path) {
        statistics.get(location).saveStats(path + location.toString() + ".json");
    }

    public void questionAnswering(Location location) {
        Question question = MyUtils.getRandomElement(questions.get(location));
        question.shuffleAnswers();
        defineButtons(question);

        printer.print(question.getBody() + "\n-------------------------------\n"
                + "Варианты ответа:\n" + question.getStringTableOfAnswers());

        int answer = getSuitableAnswer(question);

        if (question.getIsCorrect()[answer - 1]) {
            printer.printlnResponse("correctAnswer");

            statistics.get(location).updateStats(question.getNumber(), true);
        } else {
            printer.printlnResponse("incorrectAnswer");
            printer.printResponse("correctAnswerIs");
            printer.println(question.getCorrectAnswer());

            statistics.get(location).updateStats(question.getNumber(), false);
        }
    }

    private int getSuitableAnswer(Question question) {
        String ans;
        int answer;

        while (true) {
            ans = input.getInput();

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

    private <T> T[] importFromJson(String path, Class<T[]> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(path), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void defineButtons(Question question) {
        String[] array = new String[question.getAnswers().length];

        for (int i = 1; i <= question.getAnswers().length; i++) {
            array[i - 1] = String.valueOf(i);
        }

        input.defineButtons(array);
    }
}
