import java.util.Scanner;

public class JavaScriptLayer {

    public static void main(Scanner scan) {

        salutation();
        int a;
        do {
            a = work(scan);
        } while (a != 0);
    }

    public static int work(Scanner scan) {
        String command = bot.takeCommand(scan);
        int idCommand = idLib.getId(command, "JS");
        if (idCommand == -1) return 0;
        bot.quote(idCommand);
        return 1;
    }

    public static void salutation() {
        System.out.print("Готов изучать JS? Тогда пиши \"question\"!\n Ну или help, если хочешь что-то еще \n");
    }


}