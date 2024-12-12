package bot.console;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class InputService {
    private final Scanner scanner = new Scanner(System.in);
    private final Queue<String> que = new LinkedList<>();
    public boolean consoleMode = true;

    private String[] telegramButtons = null;

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
        this.telegramButtons = new String[buttons.length];
        System.arraycopy(buttons, 0, this.telegramButtons, 0, buttons.length);
    }

    public String[] getButtons() {
        String[] result = new String[telegramButtons.length];
        System.arraycopy(this.telegramButtons, 0, result, 0, result.length);
        telegramButtons = new String[0];
        return result;
    }
}