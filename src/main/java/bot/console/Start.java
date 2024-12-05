package bot.console;

import bot.telegram.TelegramBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Start {

    public static void main(String[] args) {

        String mode;
        try {
            mode = args[0];
        } catch (Exception e) {
            mode = "";
        }

        if (mode.equals("console")) {
            final ResourceStorage storage = new ResourceStorage();
            final Bot bot = new Bot(storage);

            bot.run();
        }

        if (mode.equals("telegram")) {
            try {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(new TelegramBot());
            } catch (TelegramApiException e) {
                System.out.println(e.getMessage());
            }
        }

        if (mode.equals("both")) {
            final ResourceStorage storage = new ResourceStorage();
            final Bot bot = new Bot(storage);

            new Thread(bot).start();

            try {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(new TelegramBot());
            } catch (TelegramApiException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
