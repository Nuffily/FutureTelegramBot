package bot.console;

import bot.telegram.TelegramBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Start {

    public static void main(String[] args) {

        String mode = "";
        if (args.length > 0) {
            mode = args[0];
        } else {
            System.out.print("""
                    Выберите режим работы в программных аргументах
                        console - для запуска в консоли
                        telegram - для запуска телеграм бота
                        both - для запуска и там и там""");
        }

        if (mode.equals("console")) {
            final ResourceStorage storage = new ResourceStorage();
            final OutputService outputService = new ConsoleOutputService(storage);
            final InputService inputService = new ConsoleInputService();

            final Bot bot = new Bot(storage, outputService, inputService, 0);

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
            final OutputService outputService = new ConsoleOutputService(storage);
            final InputService inputService = new ConsoleInputService();

            final Bot bot = new Bot(storage, outputService, inputService, 0);

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
