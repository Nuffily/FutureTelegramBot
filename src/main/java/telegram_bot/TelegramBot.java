package telegram_bot;

import console_bot.Bot;
import console_bot.ResourceStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram_bot.config.BotConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

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

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            else {
                users.get(chatId).input.addToQueue(messageText);
                }

            currentAnswer = users.get(chatId).printer.getAllOutput();
            sendMessage(chatId, currentAnswer);

            if (currentAnswer.equals("Пока-пока!"))
                users.remove(chatId);

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
        setButtons(sendMessage);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {

        }
    }

    public synchronized void setButtons(SendMessage sendMessage) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);


        List<KeyboardRow> keyboard = new ArrayList<>();


        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("zxc"));
        keyboardFirstRow.add(new KeyboardButton("js"));
        keyboardFirstRow.add(new KeyboardButton("math"));


        KeyboardRow keyboardSecondRow = new KeyboardRow();

        keyboardSecondRow.add(new KeyboardButton("Qwe"));


        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }

}