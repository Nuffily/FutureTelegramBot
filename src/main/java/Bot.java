import model.*;

import java.util.Scanner;

public class Bot {

    Location location = Location.MAIN;
    ResourceStorage storage;
    Scanner scan = new Scanner(System.in);
    TestService testServiceJS = new TestService(storage, Location.JS, "src/main/resources/StatisticsJS.json");
    TestService testServiceMATH = new TestService(storage, Location.MATH, "src/main/resources/StatisticsMATH.json");

    public void run() {

        String command;
        command = scan.nextLine();

        command = storage.commands.get(location).get(command);

        if (command == null) {
            PrintService.printlnResponse("unknownCommand");
            return;
        }

        PrintService.printlnResponse(command);

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
                testServiceJS.createQuestion();
                break;
            case "showStatsJS":
                testServiceJS.statistics.printStats();
                break;
            case "uploadStatsJS":
                testServiceJS.statistics = Statistics.uploadStats("src/main/resources/StatisticsJS.json");
                break;
            case "saveStatsJS":
                testServiceJS.statistics.saveStats("src/main/resources/StatisticsJS.json");
                break;
            case "MATHQuestion":
                testServiceMATH.createQuestion();
                break;
            case "showStatsMATH":
                testServiceMATH.statistics.printStats();
                break;
            case "uploadStatsMATH":
                testServiceMATH.statistics = Statistics.uploadStats("src/main/resources/StatisticsMATH.json");
                break;
            case "saveStatsMATH":
                testServiceMATH.statistics.saveStats("src/main/resources/StatisticsMATH.json");
                break;
        }
    }



}