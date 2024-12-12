package bot.console;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class NonConsoleInputService implements InputService {
    private final Queue<String> que = new LinkedList<>();

    public String getInput() {
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

    public void addToQueue(String... messages) {
        Collections.addAll(que, messages);

        synchronized (que) {
            que.notifyAll();
        }
    }
}
