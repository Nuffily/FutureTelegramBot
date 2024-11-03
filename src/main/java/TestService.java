import model.Location;
import model.Question;

import java.util.Scanner;

public class TestService {
    Question[] questions;
    ResourceStorage storage;
    QuestionStatistics statistics;
    String StatPath;
    Scanner scan = new Scanner(System.in);

    TestService(ResourceStorage storage, Location location, String StatPath) {
        this.storage = storage;
        if (location.equals(Location.JS))
            questions = importQuestions("src/main/resources/QuestionsJS.json");
        else
            questions = importQuestions("src/main/resources/QuestionsMATH.json");
        statistics = new QuestionStatistics(questions);
        this.StatPath = StatPath;
    }

    public void createQuestion() {

        Question question = MyUtils.getRandomElement(questions);

        PrintService.println(question.body + "\n-------------------------------\n"
                + "Варианты ответа:");

        String rightAnswer = question.answers[question.correctAnswer - 1];

        MyUtils.shuffleArray(question.answers);

        for (int i = 0; i < question.countOfAnswers; i++)
            if (question.answers[i].equals(rightAnswer)) {
                question.correctAnswer = i + 1;
                break;
            }

        for (int i = 0; i < question.answers.length; i++) {
            PrintService.println((i + 1) + ". " + question.answers[i]);
        }

        String ans;
        int answer;

        while (true) {
            ans = scan.nextLine();

            if (!ans.matches("[-+]?\\d+")) {
                PrintService.println("Ответ должен быть числом");
                continue;
            }

            answer = Integer.parseInt(ans);

            if (answer < 1 || answer > question.answers.length) {
                PrintService.println("Такого варианта ответа нет");
                continue;
            }

            break;
        }

        if (answer == question.correctAnswer) {
            PrintService.printlnResponse("correctAnswer");

            statistics.updateStats(question.number, true);
        }
        else {
            PrintService.printlnResponse("incorrectAnswer");
            PrintService.printResponse("correctAnswerIs");
            PrintService.println("" + question.correctAnswer);

            statistics.updateStats(question.number, false);
        }
    }

    private Question[] importQuestions(String path) {
        return ResourceStorage.importFromJSon(path, Question[].class);
    }
}
