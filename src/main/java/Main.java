import java.io.*;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        OutputLibrary library = new OutputLibrary();

        library.fillMaps();

        System.out.print("Здарова! Введи какую-нить команду, например, /help \n");


        Bot bot = new Bot();
        bot.library = library;

        while (!bot.location.equals("exit")) {

            bot.run();

        }

        System.out.print("Пока-пока!");
    }




//    public static int work(Scanner scan) {
//
//        String command = Bot.read(scan);
//
//        int idCommand = Junk.idLib.getId(command, "main");
//        if (idCommand == -1) return 0;
//        Bot.quote(idCommand);
//
//        if (idCommand == 1000) {
//            JavaScriptLayer.main(scan);
//        }
//        if (idCommand == 2000) {
//            EnglishLayer.main(scan);
//        }
//        return 1;
//    }

}
