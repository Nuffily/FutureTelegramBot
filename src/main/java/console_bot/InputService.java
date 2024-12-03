package console_bot;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class InputService {
    private final Scanner scanner = new Scanner(System.in);
    private final Queue<String> que = new LinkedList<>();
    public boolean consoleMode = true;

    public String getInput()  {
        synchronized (que) {
            if (consoleMode) {
                return scanner.nextLine();
            }
            else {
                if (que.isEmpty())
                    try {
                        que.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
}
