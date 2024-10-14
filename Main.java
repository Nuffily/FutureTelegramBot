import java.util.Scanner;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        OutputLibrary Lib = new OutputLibrary();

        Lib.fillMaps();

        System.out.println(Lib.MainQuotes.get("help"));
        System.out.println(Lib.MainQuotes.get("js"));

        salutation();
        Scanner scan = new Scanner(System.in);
        int a;
        do {
            a = work(scan);
        } while (a != 0);
    }

    public static int work(Scanner scan) {

        String command = bot.read(scan);

        int idCommand = idLib.getId(command, "main");
        if (idCommand == -1) return 0;
        bot.quote(idCommand);

        if (idCommand == 1000) {
            JavaScriptLayer.main(scan);
        }
        if (idCommand == 2000) {
            EnglishLayer.main(scan);
        }
        return 1;
    }

    public static void salutation() {
        System.out.print("Здарова! Введи какую-нить команду, например, /help \n");
    }


}
