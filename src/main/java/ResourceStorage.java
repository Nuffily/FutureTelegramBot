import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;

public class ResourceStorage {
    Map<Location, Map<String, String> > commands;
    Map<String, String> singleQuotes;
    Map<String, String[]> randomQuotes;

    ResourceStorage() {
        commands = importCommands("src/main/resources/Commands.json");
        singleQuotes = importSingleQuotes("src/main/resources/SimpleQuotes.json");
        randomQuotes = importRandomQuotes("src/main/resources/RandomQuotes.json");
    }

    private Map<Location, Map<String, String> > importCommands(String path) {

        Map<Location, Map<String, String> > map = new HashMap<>();

        Command[] commands = importFromJSon(path, Command[].class);

        for (Command currentCommand: commands) {
            if (!map.containsKey(currentCommand.location)) {
                map.put((currentCommand.location), new HashMap<>());
            }
            for (String variant: currentCommand.inputVariants) {

                map.get(currentCommand.location).put(variant, currentCommand.id);
            }
        }

        return map;
    }

    private Map<String, String[]> importRandomQuotes(String path) {

        Map<String, String[]> map = new HashMap<>();

        QuoteRandom[] quotes = importFromJSon(path, QuoteRandom[].class);

        for (QuoteRandom currentQuote: quotes)
            map.put(currentQuote.name, currentQuote.responseRandom);

        return map;
    }

    private Map<String, String> importSingleQuotes(String path) {

        HashMap<String, String> map = new HashMap<>();

        QuoteSimple[] quotes = importFromJSon(path, QuoteSimple[].class);

        for (QuoteSimple currentQuote: quotes)
            map.put(currentQuote.name, currentQuote.response);

        return map;
    }


    static public <T> T[] importFromJSon(String path, Class<T[]> clazz) {
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
