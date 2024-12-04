package bot.console;

public class Start {

    public static void main(String[] args) {

        final ResourceStorage storage = new ResourceStorage();
        final Bot bot = new Bot(storage);

        bot.run();
    }
}
