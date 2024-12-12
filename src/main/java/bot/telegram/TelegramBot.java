package bot.telegram;

import bot.console.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import bot.telegram.config.BotConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig = new BotConfig();
    private final Map<Long, Bot> users = new HashMap<>();
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (!users.containsKey(chatId)) {
                startConversation(chatId);

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                users.get(chatId).input.addToQueue(messageText);
            }

            String currentAnswer = users.get(chatId).printer.getAllOutput();

            sendMessage(chatId, currentAnswer);

            if (currentAnswer.equals("Пока-пока!\n")) {
                users.remove(chatId);
            }
        }
    }

    private void startConversation(Long chatId) {
        final OutputService outputService = new NonConsoleOutputService(storage);
        final InputService inputService = new NonConsoleInputService();

        final Bot bot = new Bot(storage, outputService, inputService);
        users.put(chatId, bot);

        new Thread(bot).start();
    }

    private void sendMessage(Long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

        setButtons(sendMessage, (users.get(chatId).getButtons()));

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized void setButtons(SendMessage sendMessage, String[] buttons) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();

        if (buttons.length <= 3) {
            for (String button : buttons) {
                keyboardFirstRow.add(new KeyboardButton(button));
            }
            keyboard.add(keyboardFirstRow);

        } else {
            KeyboardRow keyboardSecondRow = new KeyboardRow();

            for (int i = 0; i < (buttons.length - buttons.length / 2); i++) {
                keyboardFirstRow.add(new KeyboardButton(buttons[i]));
            }

            for (int i = buttons.length - buttons.length / 2; i < buttons.length; i++) {
                keyboardSecondRow.add(new KeyboardButton(buttons[i]));
            }

            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
        }

        replyKeyboardMarkup.setKeyboard(keyboard);
    }

}