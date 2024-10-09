import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.lang.Math;

public class bot {
    public static String read(Scanner scan) {
        String com = scan.nextLine();
        return com;
    }

    public static void quote(int id) {
        try(BufferedReader br = new BufferedReader(new FileReader("quotes.txt")))
        {
            String s;
            while((s=br.readLine())!=null){
                if (s.equals('$' + String.valueOf(id))) {
                    break;
                }
            }
            while((s=br.readLine())!=null){
                if (s.equals("$")) {
                    break;
                }
                System.out.println(s);
            }

        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void question(int idCommand, Scanner scan) {

        int count = idLib.getCountOfQuests(idCommand);

        if (count == 0) {
            System.out.println("Вопросов нет!.. Почему-то...");
            return;
        }

        int id = some.getRandom(idCommand + 1, idCommand + count);

        String realAnswer = printQuestion(id);
        String answer = read(scan);

        while (!answer.matches("[-+]?\\d+")) {
            System.out.println("Кажись, это не ответ");
            answer = read(scan);
        }

        if (answer.equals(realAnswer)) quote(some.getRandom(201, 211));
        else {
            quote(some.getRandom(401, 410));
            System.out.println("Правильным ответом был под номером " + realAnswer);
        }

        System.out.println();

    }

    public static String printQuestion(int id) {
        try(BufferedReader br = new BufferedReader(new FileReader("questions.txt")))
        {
            String s;
            while((s=br.readLine())!=null){
                if (s.equals('$' + String.valueOf(id))) {
                    break;
                }
            }
            System.out.println();
            while((s=br.readLine())!=null){
                if (s.equals("$V")) {
                    break;
                }
                System.out.println(s);
            }
            System.out.println();
            for (int i = 1; (s = br.readLine())!=null; i++) {
                if (s.equals("$A")) {
                    break;
                }
                System.out.println(i + ". "+ s);
            }
            return br.readLine();

        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return "1";

    }
}
