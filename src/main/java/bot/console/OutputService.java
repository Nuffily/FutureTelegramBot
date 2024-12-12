package bot.console;

import utils.MyUtils;

public abstract class OutputService {
    private final ResourceStorage storage;

    public OutputService(ResourceStorage storage) {
        this.storage = storage;
    }

    public abstract <T> void println(T quote);

    public abstract <T> void print(T quote);

    public final void printlnResponse(String command) {

        String quote = getQuote(command);

        if (quote != null) {
            println(quote);
        }
    }

    public final void printResponse(String command) {

        String quote = getQuote(command);

        if (quote != null) {
            print(quote);
        }
    }

    public final String getQuote(String command) {
        String quote = storage.getSingleReplicas().get(command);

        if (quote == null && storage.getRandomReplicas().get(command) != null) {
            quote = MyUtils.getRandomElement(storage.getRandomReplicas().get(command));
        }

        return quote;
    }

    public abstract String getAllOutput();
}
