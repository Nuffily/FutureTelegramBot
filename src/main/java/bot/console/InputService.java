package bot.console;

import bot.telegram.TelegramButtons;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class InputService {
    private final Scanner scanner = new Scanner(System.in);
    private final Queue<String> que = new LinkedList<>();
    private final TelegramButtons telegramButtons = new TelegramButtons();
    public boolean consoleMode = true;

    public String getInput() {
        if (consoleMode) {
            return scanner.nextLine();
        } else {
            synchronized (que) {
                if (que.isEmpty()) {
                    try {
                        que.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return que.remove();
            }
        }
    }

    public void addToQueue(String... messages) {
        Collections.addAll(que, messages);
        synchronized (que) {
            que.notifyAll();
        }
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void defineButtons(String... buttons) {
        telegramButtons.defineButtons(buttons);
    }

    public String[] getButtons() {
        return telegramButtons.getButtons();
    }

    public boolean isThereAnyButtons() {
        return telegramButtons.isThereButtons();
    }
}
