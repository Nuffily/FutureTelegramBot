import structures.*;

public class Start {

    public static void main(String[] args) {

        ResourceStorage library = new ResourceStorage();

        library.fillMaps();

        System.out.print("Здарова! Введи какую-нить команду, например, /help \n");


        Bot bot = new Bot();
        bot.library = library;

        while (!bot.location.equals(Location.EXIT)) {

            bot.run();

        }

        System.out.print("Пока-пока!");
    }
}
