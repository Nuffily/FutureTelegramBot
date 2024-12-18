package bot.console;

import java.util.Random;

public abstract class OutputService {
    private final ResourceStorage storage;
    private final Random random;

    public OutputService(ResourceStorage storage) {
        this.storage = storage;
        random = new Random();
    }

    public OutputService(ResourceStorage storage, Random random) {
        this.storage = storage;
        this.random = random;
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

    private String getQuote(String command) {
        String quote = storage.getSingleReplicas().get(command);

        if (quote == null && storage.getRandomReplicas().get(command) != null) {
            quote = storage.getRandomReplicas().get(command)[random.nextInt(storage.getRandomReplicas()
                    .get(command).length)];
        }

        return quote;
    }

    public abstract String getAllOutput();

    public Random getRandom() {
        return random;
    }
}
