import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import structures.*;

public class ResourceStorage {
    Map<Location, Map<String, String> > commands;
    Map<String, String> singleQuotes;
    Map<String, String[]> randomQuotes;
    Question[] JSQuestions;

    public void fillMaps() {


        commands = importCommands("src/main/java/resources/Commands.json");
        singleQuotes = importSingleQuotes("src/main/java/resources/Quotes.json");
        randomQuotes = importRandomQuotes("src/main/java/resources/Quotes.json");
        JSQuestions = importQuestions("src/main/java/resources/QuestionsJS.json");
    }

    private Question[] importQuestions(String path) {

        Question[] array = importFromJSon(path, Question[].class);

        return array;
    }

    private Map<Location, Map<String, String> > importCommands(String path) {

        Map<Location, Map<String, String> > map = new HashMap<>();

        Command[] commands = importFromJSon(path, Command[].class);

        for (Command currentCommand: commands) {
            if (!map.containsKey(Location.valueOf(currentCommand.location))) {
                map.put(Location.valueOf(currentCommand.location), new HashMap<>());
            }
            for (String variant: currentCommand.inputVariants) {

                map.get(Location.valueOf(currentCommand.location)).put(variant, currentCommand.id);
            }
        }

        return map;
    }

    private Map<String, String[]> importRandomQuotes(String path) {

        Map<String, String[]> map = new HashMap<>();

        Quote[] quotes = importFromJSon(path, Quote[].class);

        for (Quote currentQuote: quotes) {
            if (currentQuote.type == QuoteType.RANDOM)
                map.put(currentQuote.name, currentQuote.responseRandom);
        }

        return map;
    }

    private Map<String, String> importSingleQuotes(String path) {

        HashMap<String, String> map = new HashMap<>();

        Quote[] quotes = importFromJSon(path, Quote[].class);

        for (Quote currentQuote: quotes) {
            if (currentQuote.type == QuoteType.SINGLE)
                map.put(currentQuote.name, currentQuote.response);
        }

        return map;
    }

    private <T> T[] importFromJSon(String path, Class<T[]> clazz) {
        T[] array;
        File file = new File(path);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            array = objectMapper.readValue(file, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return array;
    }
}
