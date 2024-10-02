import java.util.Scanner;
import java.io.*;

public class Main {

    public static void main(String[] args) {

        salutation();
        Scanner in = new Scanner(System.in);
        int a;
        do {
            a = work(in);
        } while (a != 0);
    }

    public static int work(Scanner in) {
        String command = bot.takeCommand(in);
        int idCommand = bot.commandToId(command);
        if (idCommand == -1) return 0;
        bot.quote(idCommand);
        return 1;
    }

    public static void salutation() {
        System.out.print("Здарова! Введи какую-нить команду, например, /help \n");
    }

    public static class bot {
        public static String takeCommand(Scanner in) {
            String com = in.nextLine();
            return com;
        }

        public static int commandToId(String command) {
            switch (command) {
                case "exit":
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

        public static void quote(int id) {
            try(BufferedReader br = new BufferedReader(new FileReader("quotes.txt")))
            {
                String s;
                while((s=br.readLine())!=null){

                    if (s.equals('$' + String.valueOf(id))) {
                        break;
                    }
                }

                while((s=br.readLine())!=null){
                    if (s.equals("$")) {
                        break;
                    }
                    System.out.println(s);
                }


            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }

    }

}
