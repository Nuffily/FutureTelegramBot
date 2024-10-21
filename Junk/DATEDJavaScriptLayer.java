//import java.util.Scanner;
//
//public class JavaScriptLayer {
//
//    public static void main(Scanner scan) {
//
//        salutation();
//        int a;
//        do {
//            a = work(scan);
//        } while (a != 0);
//    }
//
//    public static int work(Scanner scan) {
//        String command = Bot.read(scan);
//        int idCommand = Junk.idLib.getId(command, "JS");
//        if (idCommand == -1) return 0;
//        if (idCommand < 100000) Bot.quote(idCommand);
//        else Bot.question(idCommand, scan);
//        return 1;
//    }
//
//    public static void salutation() {
//        System.out.print("Готов изучать JS? Тогда пиши \"quest\"!\nНу или help, если хочешь что-то еще \n");
//    }
//
//
//}