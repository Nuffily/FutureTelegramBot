import structures.*;

public class Start {

    public static void main(String[] args) {

        ResourceStorage storage = new ResourceStorage();

        storage.fillMaps();

        PrintService.println("Здарова! Введи какую-нить команду, например, /help");

        PrintService.storage = storage;

        Bot bot = new Bot();
        bot.storage = storage;

        while (!bot.location.equals(Location.EXIT)) {

            bot.run();

        }

        PrintService.println("Пока-пока!");
    }
}
