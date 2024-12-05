package bot.console;

import utils.MyUtils;

import java.util.LinkedList;
import java.util.Queue;

public class OutputService {
    private final ResourceStorage storage;
    private final Queue<String> que = new LinkedList<>();
    public boolean consoleMode = true;


    public OutputService(ResourceStorage storage) {
        this.storage = storage;
    }

    public void printlnResponse(String command) {

        String quote = getQuote(command);

        if (quote != null) {
            println(quote);
        }
    }

    public void printResponse(String command) {

        String quote = getQuote(command);

        if (quote != null) {
            print(quote);
        }
    }

    public String getQuote(String command) {
        String quote = storage.getSingleReplicas().get(command);

        if (quote == null && storage.getRandomReplicas().get(command) != null) {
            quote = MyUtils.getRandomElement(storage.getRandomReplicas().get(command));
        }

        return quote;
    }

    public <T> void println(T quote) {
        if (consoleMode) {
            System.out.println(quote);
        } else {
            que.add(quote + "\n");
        }
    }

    public <T> void print(T quote) {
        if (consoleMode) {
            System.out.print(quote);
        } else {
            que.add(String.valueOf(quote));
        }
    }

    public String getOutput() {
        return que.remove();
    }

    public String getAllOutput() {
        StringBuilder result = new StringBuilder();

        while (!que.isEmpty()) {
            result.append(que.remove());
        }

        return result.toString();
    }
}
