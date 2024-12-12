package bot.console;

import utils.MyUtils;

import java.util.LinkedList;
import java.util.Queue;

public class ConsoleOutputService extends OutputService {

    public ConsoleOutputService(ResourceStorage storage) {
        super(storage);
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
