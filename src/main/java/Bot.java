import structures.*;

import java.util.Scanner;

public class Bot {

    Location location = Location.MAIN;
    ResourceStorage storage;
    Scanner scan = new Scanner(System.in);

    public void run() {

        String command;
        command = scan.nextLine();

        command = storage.commands.get(location).get(command);

        if (command == null) {
            PrintService.printlnQuote("unknownCommand");
            return;
        }

        PrintService.printlnQuote(command);

        execute(command);

    }

    private void execute(String command) {
        switch (command) {
            case "travelToJS":
                location = Location.JS;
                break;
            case "toMenu":
                location = Location.MAIN;
                break;
            case "exit":
                location = Location.EXIT;
                break;
            case "JSQuestion":
                createQuestion();
                break;
        }
    }

    private void createQuestion() {

        Question question = MyUtils.getRandomElement(storage.JSQuestions);

        Scanner scan = new Scanner(System.in);

        PrintService.println(question.body + "-------------------------------\n"
                + "Варианты ответа:");

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

        if (answer == question.correctAnswer)
            PrintService.printlnQuote("correctAnswer");
        else {
            PrintService.printlnQuote("incorrectAnswer");
            PrintService.printQuote("correctAnswerIs");
            PrintService.println("" + question.correctAnswer);
        }
    }

}