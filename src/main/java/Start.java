import structures.*;

public class Start {

    public static void main(String[] args) {

        ResourceStorage storage = new ResourceStorage();
        
        Statistics statisticsJS = new Statistics(storage, storage.JSQuestions);

        PrintService.println("Здарова! Введи какую-нить команду, например, /help");

        PrintService.storage = storage;

        Bot bot = new Bot();
        bot.storage = storage;
        bot.statisticsJS = statisticsJS;

        while (!bot.location.equals(Location.EXIT)) {

            bot.run();

        }

        PrintService.println("Пока-пока!");
    }
}
