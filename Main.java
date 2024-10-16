import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        OutputLibrary Lib = new OutputLibrary();

        Lib.fillMaps();

        System.out.print("Здарова! Введи какую-нить команду, например, /help \n");

        Scanner scan = new Scanner(System.in);
        String command;
        Bot.Lib = Lib;

        while (!Bot.location.equals("exit")) {

            command = scan.nextLine();

            Bot.run(command);
        }


    }




//    public static int work(Scanner scan) {
//
//        String command = Bot.read(scan);
//
//        int idCommand = Junk.idLib.getId(command, "main");
//        if (idCommand == -1) return 0;
//        Bot.quote(idCommand);
//
//        if (idCommand == 1000) {
//            JavaScriptLayer.main(scan);
//        }
//        if (idCommand == 2000) {
//            EnglishLayer.main(scan);
//        }
//        return 1;
//    }

}
