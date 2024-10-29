import java.util.Scanner;

public class Bot {

    public String location = "main";
    public OutputLibrary library;

    Scanner scan = new Scanner(System.in);

    public void run() {

        String command;
        command = scan.nextLine();

        command = library.commands.get(location).get(command);

        if (command == null) {
            System.out.println(handMadeUtils.getRndElem(library.randomQuotes.get("unknownCommand")));
            return;
        }

        String quote;
        quote = library.singleQuotes.get(command);
        if ((quote == null) && (library.randomQuotes.get(command) != null)) {
            quote = handMadeUtils.getRndElem(library.randomQuotes.get(command));
        }

        if (quote != null) System.out.println(quote);

        execute(command);

    }

    private void execute(String command) {
        switch (command) {
            case "travelToJS":
                location = "js";
                break;
            case "toMenu":
                location = "main";
                break;
            case "exit":
                location = "exit";
                break;
            case "JSQuestion":
                createQuestion();
                break;
        }
    }

    private void createQuestion() {

        Question question = handMadeUtils.getRndElem(library.JSQuestions);

        Scanner scan = new Scanner(System.in);

        System.out.println(question.body + "-------------------------------\n"
                + "Варианты ответа:");

        for (int i = 0; i < question.answers.length; i++) {
            System.out.println(i + ". " + question.answers[i]);
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
            System.out.println(handMadeUtils.getRndElem(library.randomQuotes.get("correctAnswer")));
        else
            System.out.println(handMadeUtils.getRndElem(library.randomQuotes.get("incorrectAnswer"))
                    + "\nПравильным ответом был вариант " + question.correctAnswer);
    }

}
