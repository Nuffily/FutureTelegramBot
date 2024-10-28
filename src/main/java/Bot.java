import structures.*;

import java.util.Scanner;

public class Bot {

    Location location = Location.MAIN;
    ResourceStorage library;
    Scanner scan = new Scanner(System.in);

    public void run() {

        String command;
        command = scan.nextLine();

        command = library.commands.get(location).get(command);

        if (command == null) {
            System.out.println(MyUtils.getRandomElement(library.randomQuotes.get("unknownCommand")));
            return;
        }

        String quote;
        quote = library.singleQuotes.get(command);
        if ((quote == null) && (library.randomQuotes.get(command) != null)) {
            quote = MyUtils.getRandomElement(library.randomQuotes.get(command));
        }

        if (quote != null) System.out.println(quote);

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

        Question question = MyUtils.getRandomElement(library.JSQuestions);

        Scanner scan = new Scanner(System.in);

        System.out.println(question.body + "-------------------------------\n"
                + "Варианты ответа:");

        for (int i = 0; i < question.answers.length; i++) {
            System.out.println((i + 1) + ". " + question.answers[i]);
        }

        String ans;
        int answer;

        while (true) {
            ans = scan.nextLine();

            if (!ans.matches("[-+]?\\d+")) {
                System.out.println("Ответ должен быть числом");
                continue;
            }

            answer = Integer.parseInt(ans);

            if (answer < 1 || answer > question.answers.length) {
                System.out.println("Такого варианта ответа нет");
                continue;
            }

            break;
        }

        if (answer == question.correctAnswer)
            System.out.println(MyUtils.getRandomElement(library.randomQuotes.get("correctAnswer")));
        else
            System.out.println(MyUtils.getRandomElement(library.randomQuotes.get("incorrectAnswer"))
                    + "\nПравильным ответом был вариант " + question.correctAnswer);
    }

}