package bot.console;

import java.util.Random;

public class ConsoleOutputService extends OutputService {

    public ConsoleOutputService(ResourceStorage storage) {
        super(storage);
    }

    public ConsoleOutputService(ResourceStorage storage, Random random) {
        super(storage, random);
    }

    public <T> void println(T quote) {
        System.out.println(quote);
    }

    public <T> void print(T quote) {
        System.out.print(quote);
    }

    public String getAllOutput() {
        System.out.print("метод getAllOutput не доступнен в консольном режиме");
        return null;
    }
}
