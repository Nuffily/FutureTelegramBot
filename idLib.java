public class idLib {
    public static int getId(String command) {
        switch (command) {
            case "exit":
                System.out.println("Пока-пока!");
                return -1;
            case "help":
                return 7;
            case "javascript":
                return 1000;
            default:
                System.out.println("Я же сказал хелп введи, если не шаришь");
                return 0;
        }
    }
}
