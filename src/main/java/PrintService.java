public class PrintService {

    static ResourceStorage storage;

    public static void printlnQuote(String command) {

        String quote = storage.singleQuotes.get(command);

        if ((quote == null) && (storage.randomQuotes.get(command) != null)) {
            quote = MyUtils.getRandomElement(storage.randomQuotes.get(command));
        }

        if (quote != null) System.out.println(quote);
    }

    public static void printQuote(String command) {

        String quote = storage.singleQuotes.get(command);

        if ((quote == null) && (storage.randomQuotes.get(command) != null)) {
            quote = MyUtils.getRandomElement(storage.randomQuotes.get(command));
        }

        if (quote != null) System.out.print(quote);
    }

    public static void println(String quote) {
        System.out.println(quote);
    }

    public static void print(String quote) {
        System.out.print(quote);
    }
}
