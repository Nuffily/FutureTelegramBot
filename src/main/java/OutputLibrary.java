import java.io.*;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OutputLibrary {
    HashMap<String, HashMap<String, String> > commands;
    HashMap<String, String> singleQuotes;
    HashMap<String, String[]> randomQuotes;
    Question[] JSQuestions;

    public void fillMaps() {
        commands = importCommands("src/main/java/data/Commands.json");
        singleQuotes = importSingleQuotes("src/main/java/data/Quotes.json");
        randomQuotes = importRandomQuotes("src/main/java/data/Quotes.json");
        JSQuestions = importQuestions("src/main/java/data/QuestionsJS.json");
    }

    private Question[] importQuestions(String path) {
        Question[] array;
        File file = new File(path);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        try {
            array = objectMapper.readValue(file, Question[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return array;
    }

    private HashMap<String, HashMap<String, String> > importCommands(String path) {
        HashMap<String, HashMap<String, String> > map = new HashMap<>();
        File file = new File(path);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        Command[] commands;

        try {
            commands = objectMapper.readValue(file, Command[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Command currentCommand: commands) {
            if (!map.containsKey(currentCommand.location)) {
                map.put(currentCommand.location, new HashMap<>());
            }
            for (String variant: currentCommand.inputVariants) {

                map.get(currentCommand.location).put(variant, currentCommand.id);
            }
        }

        return map;
    }

    private HashMap<String, String[]> importRandomQuotes(String path) {

        HashMap<String, String[]> map = new HashMap<>();
        File file = new File(path);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        Quote[] quotes;

        try {
            quotes = objectMapper.readValue(file, Quote[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Quote currentQuote: quotes) {
            if (currentQuote.type.equals("random"))
                map.put(currentQuote.name, currentQuote.outputArray);
        }

        return map;
    }

    private HashMap<String, String> importSingleQuotes(String path) {

        HashMap<String, String> map = new HashMap<>();
        File file = new File(path);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        Quote[] quotes;

        try {
            quotes = objectMapper.readValue(file, Quote[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Quote currentQuote: quotes) {
            if (currentQuote.type.equals("single"))
                map.put(currentQuote.name, currentQuote.output);
        }

        return map;
    }

}