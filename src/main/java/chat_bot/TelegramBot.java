package chat_bot;

import console_bot.Bot;
import console_bot.PrintService;
import console_bot.ResourceStorage;
import lombok.AllArgsConstructor;
import model.Location;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import chat_bot.config.BotConfig;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private BotConfig botConfig;
    final private Map<Long, Bot> users = new HashMap<>();
    private final ResourceStorage storage = new ResourceStorage();

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        String currentAnswer;

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (!users.containsKey(chatId)) {
                startConversation(chatId);
            }

            else {
                users.get(chatId).input.addToQueue(messageText);

                System.out.println(users.get(chatId).printer.que);

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                while (!users.get(chatId).printer.que.isEmpty()) {
                    currentAnswer = users.get(chatId).printer.que.remove();
                    sendMessage(chatId, currentAnswer);
                    if (currentAnswer.equals("Пока-пока!"))
                        users.remove(chatId);

                }

            }
        }

    }

    private void startConversation(Long chatId) {
        final Bot bot = new Bot(storage);
        users.put(chatId, bot);
        bot.printer.consoleMode = false;
        bot.input.consoleMode = false;
        new Thread(bot).start();

    }

    private void sendMessage(Long chatId, String textToSend){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {

        }
    }
}