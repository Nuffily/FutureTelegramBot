import java.util.Scanner;

public class EnglishLayer {

    public static void main(Scanner scan) {

        salutation();
        int a;
        do {
            a = work(scan);
        } while (a != 0);
    }

    public static int work(Scanner scan) {
        String command = bot.read(scan);
        int idCommand = idLib.getId(command, "ENG");
        if (idCommand == -1) return 0;
        if (idCommand < 100000) bot.quote(idCommand);
        else bot.question(idCommand, scan);
        return 1;
    }

    public static void salutation() {
        System.out.print("Готов изучать Английский? Тогда пиши \"quest\"!\nНу или help, если хочешь что-то еще \n");
    }


}