package console_bot;

import utils.MyUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class PrintService {
    private final ResourceStorage storage;
    public final Queue<String> que = new LinkedList<>();
    public boolean consoleMode = true;


    public PrintService(ResourceStorage storage) {
        this.storage = storage;
    }

    public PrintService() {
        storage = null;
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

        if ((quote == null) && (storage.getRandomReplicas().get(command) != null)) {
            quote = MyUtils.getRandomElement(storage.getRandomReplicas().get(command));
        }

        return quote;
    }

    public void println(String quote) {
        if (consoleMode)
            System.out.println(quote);
        else
            que.add(quote + "\n");
    }

    public void print(String quote) {
        if (consoleMode)
            System.out.print(quote);
        else
            que.add(quote);
    }
}
