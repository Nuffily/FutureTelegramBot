import model.*;


import java.util.Scanner;

public class Bot {

    Location location = Location.MAIN;
    ResourceStorage storage;
    Scanner scan = new Scanner(System.in);
    TestService testServiceJS;
    TestService testServiceMATH;
    PrintService printer = new PrintService();

    Bot(ResourceStorage storage) {
        this.storage = storage;
        testServiceJS = new TestService(storage, Location.JS, "src/main/resources/StatisticsJS.json");
        testServiceMATH = new TestService(storage, Location.MATH, "src/main/resources/StatisticsMATH.json");
    }

    public void run() {

        String command;
        command = scan.nextLine();

        command = storage.getCommands().get(location).get(command);

        if (command == null) {
            printer.printlnResponse("unknownCommand", storage);
            return;
        }

        printer.printlnResponse(command, storage);


        execute(command);
    }

    private void execute(String command) {
        switch (command) {
            case "travelToJS":
                location = Location.JS;
                break;
            case "travelToMATH":
                location = Location.MATH;
                break;
            case "toMenu":
                location = Location.MAIN;
                break;
            case "exit":
                location = Location.EXIT;
                break;
            case "JSQuestion":
                testServiceJS.questionPassion();
                break;
            case "showStatsJS":
                testServiceJS.statistics.printStats();
                break;
            case "uploadStatsJS":
                testServiceJS.statistics.uploadStats("src/main/resources/StatisticsJS.json");
                break;
            case "saveStatsJS":
                testServiceJS.statistics.saveStats("src/main/resources/StatisticsJS.json");
                break;
            case "MATHQuestion":
                testServiceMATH.questionPassion();
                break;
            case "showStatsMATH":
                testServiceMATH.statistics.printStats();
                break;
            case "uploadStatsMATH":
                testServiceMATH.statistics.uploadStats("src/main/resources/StatisticsMATH.json");
                break;
            case "saveStatsMATH":
                testServiceMATH.statistics.saveStats("src/main/resources/StatisticsMATH.json");
                break;
        }
    }

}