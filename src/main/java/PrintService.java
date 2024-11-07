public class PrintService {
    static ResourceStorage storage;

    public void printlnResponse(String command) {

        String quote = getQuote(command);

        if (quote != null) println(getQuote(command));
    }

    public void printResponse(String command) {

        String quote = getQuote(command);

        if (quote != null) print(getQuote(command));
    }

    private String getQuote(String command) {
        String quote = storage.singleQuotes.get(command);

        if ((quote == null) && (storage.randomQuotes.get(command) != null)) {
            quote = MyUtils.getRandomElement(storage.randomQuotes.get(command));
        }

        return quote;
    }

    public <T> void println(T quote) {
        System.out.println(quote);
    }

    public<T> void print(T quote) {
        System.out.print(quote);
    }
}
