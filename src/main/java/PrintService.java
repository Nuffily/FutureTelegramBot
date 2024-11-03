public class PrintService {

    static ResourceStorage storage;

    public static void printlnResponse(String command) {

        String quote = getQuote(command);

        if (quote != null) println(quote);
    }

    public static void printResponse(String command) {

        String quote = getQuote(command);

        if (quote != null) print(quote);
    }

    private static String getQuote(String command) {
        String quote = storage.singleQuotes.get(command);

        if ((quote == null) && (storage.randomQuotes.get(command) != null)) {
            quote = MyUtils.getRandomElement(storage.randomQuotes.get(command));
        }

        return quote;
    }

    public static <T> void println(T quote) {
        System.out.println(quote);
    }

    public static <T> void print(T quote) {
        System.out.print(quote);
    }
}
