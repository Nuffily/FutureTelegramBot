import java.util.Scanner;

public class Bot {

    String location = "main";
    OutputLibrary library;
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



//    public static void quote(int id) {
//        try(BufferedReader br = new BufferedReader(new FileReader("quotes.txt")))
//        {
//            String s;
//            while((s=br.readLine())!=null){
//                if (s.equals('$' + String.valueOf(id))) {
//                    break;
//                }
//            }
//            while((s=br.readLine())!=null){
//                if (s.equals("$")) {
//                    break;
//                }
//                System.out.println(s);
//            }
//
//        }
//        catch(IOException ex){
//            System.out.println(ex.getMessage());
//        }
//    }

//    public static void question(int idCommand, Scanner scan) {
//
//        int count = Junk.idLib.getCountOfQuests(idCommand);
//
//        if (count == 0) {
//            System.out.println("Вопросов нет!.. Почему-то...");
//            return;
//        }
//
//        int id = Some.getRandom(idCommand + 1, idCommand + count);
//
//        String realAnswer = printQuestion(id);
//        String answer = read(scan);
//
//        while (!answer.matches("[-+]?\\d+")) {
//            System.out.println("Кажись, это не ответ");
//            answer = read(scan);
//        }
//
//        if (answer.equals(realAnswer)) quote(Some.getRandom(201, 211));
//        else {
//            quote(Some.getRandom(401, 410));
//            System.out.println("Правильным ответом был под номером " + realAnswer);
//        }
//
//        System.out.println();
//
//    }

//    public static String printQuestion(int id) {
//        try(BufferedReader br = new BufferedReader(new FileReader("DATEDquestions.txt")))
//        {
//            String s;
//            while((s=br.readLine())!=null){
//                if (s.equals('$' + String.valueOf(id))) {
//                    break;
//                }
//            }
//            System.out.println();
//            while((s=br.readLine())!=null){
//                if (s.equals("$V")) {
//                    break;
//                }
//                System.out.println(s);
//            }
//            System.out.println();
//            for (int i = 1; (s = br.readLine())!=null; i++) {
//                if (s.equals("$A")) {
//                    break;
//                }
//                System.out.println(i + ". "+ s);
//            }
//            return br.readLine();
//
//        }
//        catch(IOException ex){
//            System.out.println(ex.getMessage());
//        }
//        return "1";
//
//    }
}
