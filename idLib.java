import java.util.Scanner;

public class idLib {
    public static int getId(String command, String from) {
        switch (from) {
            case "main":
                switch (command) {
                    case "exit":
                        System.out.println("Пока-пока!");
                        return -1;
                    case "help":
                        return 7;
                    case "javascript":
                        return 1000;
                    default:
                        return some.getRandom(11, 17);
                }

            case "JS":
                switch (command) {
                    case "back":
                        System.out.println("Назад в меню!");
                        return -1;
                    case "help":
                        return 8;
                    default:
                        return some.getRandom(11, 17);
                }

        }

        return 0;
    }
}
