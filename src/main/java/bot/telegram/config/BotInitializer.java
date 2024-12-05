package bot.telegram.config;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.TelegramBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class BotInitializer {
    private final TelegramBot telegramBot;
    public BotInitializer(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot((LongPollingBot) telegramBot);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }
}