import model.Location;

public class Start {

    public static void main(String[] args) {

        final ResourceStorage storage = new ResourceStorage();
        final PrintService printer = new PrintService(storage);
        final Bot bot = new Bot(storage);

        printer.println("Здарова! Введи какую-нить команду, например, /help");

        while (!bot.getLocation().equals(Location.EXIT)) {
            bot.run();
        }

        printer.println("Пока-пока!");
    }
}