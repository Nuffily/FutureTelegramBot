import model.Location;

public class Start {

    public static void main(String[] args) {

        ResourceStorage storage = new ResourceStorage();

        PrintService printer = new PrintService(storage);

        printer.println("Здарова! Введи какую-нить команду, например, /help");

        Bot bot = new Bot(storage);

        bot.theoryService = new TheoryService();

        while (!bot.location.equals(Location.EXIT)) {
            bot.run();
        }

        printer.println("Пока-пока!");
    }
}