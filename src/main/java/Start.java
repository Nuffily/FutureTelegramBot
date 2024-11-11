import model.*;

public class Start {

    public static void main(String[] args) {

        ResourceStorage storage = new ResourceStorage();

        PrintService printer = new PrintService();

        printer.println("Здарова! Введи какую-нить команду, например, /help");

        Bot bot = new Bot(storage);

        while (!bot.location.equals(Location.EXIT)) {

            bot.run();

        }

        printer.println("Пока-пока!");
    }
}
