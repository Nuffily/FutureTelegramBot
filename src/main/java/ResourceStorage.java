import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;

public class ResourceStorage {
    private Map<Location, Map<String, String> > commands;
    private Map<String, String> singleQuotes;
    private Map<String, String[]> randomQuotes;
    private final ObjectMapper mapper = new ObjectMapper();

    ResourceStorage() {
        commands = importCommands("src/main/resources/Commands.json");
        singleQuotes = importSingleQuotes("src/main/resources/SimpleQuotes.json");
        randomQuotes = importRandomQuotes("src/main/resources/RandomQuotes.json");
    }

    private Map<Location, Map<String, String> > importCommands(String path) {

        Map<Location, Map<String, String> > map = new HashMap<>();

        Command[] commands = importFromJson(path, Command[].class);

        for (Command currentCommand: commands) {
            if (!map.containsKey(currentCommand.getLocation())) {
                map.put((currentCommand.getLocation()), new HashMap<>());
            }
            for (String variant: currentCommand.getInputVariants()) {

                map.get(currentCommand.getLocation()).put(variant, currentCommand.getId());
            }
        }

        return map;
    }

    private Map<String, String[]> importRandomQuotes(String path) {

        Map<String, String[]> map = new HashMap<>();

        QuoteRandom[] quotes = importFromJson(path, QuoteRandom[].class);

        for (QuoteRandom currentQuote: quotes)
            map.put(currentQuote.getName(), currentQuote.getResponseRandom());

        return map;
    }

    private Map<String, String> importSingleQuotes(String path) {

        HashMap<String, String> map = new HashMap<>();

        QuoteSimple[] quotes = importFromJson(path, QuoteSimple[].class);

        for (QuoteSimple currentQuote: quotes)
            map.put(currentQuote.getName(), currentQuote.getResponse());

        return map;
    }

    public <T> T[] importFromJson(String path, Class<T[]> clazz) {
        try {
            return mapper.readValue(new File(path), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Location, Map<String, String> > getCommands() {
        return commands;
    }

    public Map<String, String> getSingleQuotes() {
        return singleQuotes;
    }

    public Map<String, String[]> getRandomQuotes() {
        return randomQuotes;
    }
}
