package bot.console;

import java.util.Scanner;

public class ConsoleInputService implements InputService {
    private final Scanner scanner = new Scanner(System.in);

    public String getInput() {
        return scanner.nextLine();
    }

    public void addToQueue(String... messages) {
        System.out.print("метод addToQueue не доступнен в консольном режиме");
    }

}
