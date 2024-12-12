package bot.console;

import java.util.LinkedList;
import java.util.Queue;

public class NonConsoleOutputService extends OutputService{
    private final Queue<String> que = new LinkedList<>();

    public NonConsoleOutputService(ResourceStorage storage) {
        super(storage);
    }

    public <T> void println(T quote) {
        que.add(quote + "\n");
    }

    public <T> void print(T quote) {
        que.add(String.valueOf(quote));
    }

    public String getAllOutput() {
        StringBuilder result = new StringBuilder();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while (!que.isEmpty()) {
            result.append(que.remove());
        }

        return result.toString();
    }
}
