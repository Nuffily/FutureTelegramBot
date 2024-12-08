package bot.console;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.Location;
import model.Question;
import model.State;
import utils.MyUtils;

public class TestService {
    private final Map<Location, Question[]> questions;
    private final Map<Location, QuestionStatistics> statistics;
    private final OutputService printer;
    private final InputService input;
    private final ResourceStorage storage;
    private final SettingsService settings;

    public TestService(OutputService printer, InputService inputService, ResourceStorage storage, SettingsService settings) {
        this.printer = printer;
        this.input = inputService;
        this.storage = storage;
        this.settings = settings;

        questions = new HashMap<>();
        questions.put(Location.JS, importQuestions("src/main/resources/QuestionsJS.json"));
        questions.put(Location.MATH, importQuestions("src/main/resources/QuestionsMATH.json"));

        statistics = new HashMap<>();
        statistics.put(Location.JS, new QuestionStatistics(questions.get(Location.JS), printer));
        statistics.put(Location.MATH, new QuestionStatistics(questions.get(Location.MATH), printer));
    }

//    public Map<Location, QuestionStatistics> getStatistics() {
//        return statistics;
//    }

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

        Question question;
        if (settings.getRepeatSettings() == State.OFF){
            question = MyUtils.getRandomElement(questions.get(location));
        }
        else{
            question = settings.GetNotRepeatQuestion(questions.get(location), statistics.get(location).getQuestionsAttempts());
        }
        question.shuffleAnswers();
        defineButtons(question);

        printer.print(question.getBody() + "\n-------------------------------\n"
                + "Варианты ответа:\n" + question.getStringTableOfAnswers());

        int answer = getSuitableAnswer(location, question);

        if (answer == 0) {
            printer.println(question.getExplanation());
            printer.printResponse("correctAnswerIs");
            printer.println(question.getCorrectAnswer());
            return;
        }

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

    private int getSuitableAnswer(Location location, Question question) {
        String ans;
        int answer;

        while (true) {
            ans = input.getInput();

            if (storage.translateCommand(ans, location) != null && storage.translateCommand(ans, location).equals("explanationQuestion")) {
                answer = 0;
                return answer;
            }

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
        String[] array = new String[question.getAnswers().length + 1];

        int CountOfButtons = question.getAnswers().length + 1;

        for (int i = 1; i <= question.getAnswers().length; i++) {
            array[i - 1] = String.valueOf(i);
        }
        array[CountOfButtons - 1] = "ответ";

        input.defineButtons(array);
    }
}