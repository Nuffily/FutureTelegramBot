import utils.MyUtils;

public class PrintService {

    public void printlnResponse(String command, ResourceStorage storage) {

        String quote = getQuote(command, storage);

        if (quote != null) println(quote);
    }

    public void printResponse(String command, ResourceStorage storage) {

        String quote = getQuote(command, storage);

        if (quote != null) print(quote);
    }

    private String getQuote(String command, ResourceStorage storage) {
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
