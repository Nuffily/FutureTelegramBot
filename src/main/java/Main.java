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
}
