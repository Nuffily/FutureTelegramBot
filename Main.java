import java.util.Scanner;
import java.io.*;

public class Main {

    public static void main(String[] args) {

        salutation();
        Scanner scan = new Scanner(System.in);
        int a;
        do {
            a = work(scan);
        } while (a != 0);
    }

    public static int work(Scanner scan) {
        String command = bot.takeCommand(scan);
        int idCommand = idLib.getId(command);
        if (idCommand == -1) return 0;
        bot.quote(idCommand);
        return 1;
    }

    public static void salutation() {
        System.out.print("Здарова! Введи какую-нить команду, например, /help \n");
    }


}
