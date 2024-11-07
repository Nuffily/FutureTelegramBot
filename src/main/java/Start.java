import model.*;

public class Start {

    public static void main(String[] args) {

        ResourceStorage storage = new ResourceStorage();

        PrintService.println("Здарова! Введи какую-нить команду, например, /help");

        PrintService.storage = storage;

        Bot bot = new Bot();
        bot.storage = storage;

        bot.theoryService = new TheoryService(storage, Location.THEORY);
        while (!bot.location.equals(Location.EXIT)) {

            bot.run();

        }

        PrintService.println("Пока-пока!");
    }
}
