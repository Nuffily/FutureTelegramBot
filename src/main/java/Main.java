import java.io.*;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        OutputLibrary lib = new OutputLibrary();
        lib.fillMaps();

        System.out.print("Здорова! Введи какую-нить команду, например, /help \n");

        Bot bot = new Bot();
        bot.library = lib;

        while (!bot.location.equals("exit")) {
            bot.run();

        }

        System.out.print("Пока-пока!");
    }

}
