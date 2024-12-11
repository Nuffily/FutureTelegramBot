package bot.console;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Location;
import model.Question;
import utils.MyUtils;

public class TestService {
    private final Map<Location, Question[]> questions;
    private final Map<Location, QuestionStatistics> statistics;
    private final OutputService printer;
    private final InputService input;
    private final ResourceStorage storage;
    private final SettingsService settings;
    private Question lastQuestion;

    public TestService(OutputService printer, InputService inputService,
                       ResourceStorage storage, SettingsService settings) {
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

        Question question = getQuestion(location);

        question.shuffleAnswers();
        defineButtons(question);

        printer.print(question.getBody() + "\n-------------------------------\n"
                + "Варианты ответа:\n" + question.getStringTableOfAnswers());

        int answer = getSuitableAnswer(location, question);

        questionAnsweringResult(answer, question, location);

        lastQuestion = question;
    }

    private void questionAnsweringResult(int answer, Question question, Location location) {
        if (answer == 0) {
            printer.printResponse("correctAnswerIs");
            printer.println(question.getCorrectAnswer());
            printer.println("-------------------------------\n" + question.getExplanation());

            statistics.get(location).updateStats(question.getNumber(), false);
        } else if (question.getIsCorrect()[answer - 1]) {
            printer.printlnResponse("correctAnswer");

            statistics.get(location).updateStats(question.getNumber(), true);
        } else {
            printer.printlnResponse("incorrectAnswer");

            if (settings.getShowAnswer()) {
                printer.printResponse("correctAnswerIs");
                printer.println(question.getCorrectAnswer());
            }

            if (settings.getShowExplanation()) {
                printer.println("-------------------------------\n" + question.getExplanation());
            }

            statistics.get(location).updateStats(question.getNumber(), false);
        }
    }

    private int getSuitableAnswer(Location location, Question question) {
        String ans;
        int answer;

        while (true) {
            ans = input.getInput();

            if (storage.translateCommand(ans, location).equals("surrenderQuestion")) {
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

        int countOfButtons = question.getAnswers().length + 1;

        for (int i = 1; i <= question.getAnswers().length; i++) {
            array[i - 1] = String.valueOf(i);
        }
        array[countOfButtons - 1] = "Ответ";

        input.defineButtons(array);
    }

    public void showLastExplanation() {
        if (lastQuestion != null) {
            printer.printResponse("correctAnswerIs");
            printer.println(lastQuestion.getCorrectAnswer() + " ("
                    + lastQuestion.getAnswers()[lastQuestion.getCorrectAnswer() - 1] + ")");
            printer.println("-------------------------------\n" + lastQuestion.getExplanation());
        } else {
            printer.println("Сначала попробуй пройти хоть один тест");
        }
    }

    public Question getQuestion(Location location) {

        List<Integer> listQuestions = new ArrayList<>();

        if (!settings.getRepeatSolved()) {
            for (int i = 1; i <= questions.get(location).length; i++) {
                if (!statistics.get(location).getQuestionPassed()[i]) {
                    listQuestions.add(i);
                }
            }
        } else if (!settings.getRepeatQuestions()) {
            for (int i = 1; i <= questions.get(location).length; i++) {
                if (statistics.get(location).getQuestionsAttempts()[i] == 0) {
                    listQuestions.add(i);
                }
            }
        }

        if (!listQuestions.isEmpty()) {
            return questions.get(location)[listQuestions.get(MyUtils.getRandom(0, listQuestions.size())) - 1];
        } else {
            return MyUtils.getRandomElement(questions.get(location));
        }
    }
}