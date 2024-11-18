import utils.MyUtils;

public class PrintService {
    private final ResourceStorage storage;

    PrintService(ResourceStorage storage) {
        this.storage = storage;
    }

    PrintService() {
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

    private String getQuote(String command) {
        String quote = storage.getSingleQuotes().get(command);

        if ((quote == null) && (storage.getRandomQuotes().get(command) != null)) {
            quote = MyUtils.getRandomElement(storage.getRandomQuotes().get(command));
        }

        return quote;
    }

    public <T> void println(T quote) {
        System.out.println(quote);
    }

    public <T> void print(T quote) {
        System.out.print(quote);
    }

}
