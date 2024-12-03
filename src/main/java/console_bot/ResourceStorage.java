package console_bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import model.Command;
import model.Location;
import model.ReplicaRandom;
import model.ReplicaSimple;

public class ResourceStorage {
    private final Map<Location, Map<String, String>> commands;
    private final Map<String, String> singleReplicas;
    private final Map<String, String[]> randomReplicas;
    private final ObjectMapper mapper = new ObjectMapper();

    public ResourceStorage() {
        commands = importCommands("src/main/resources/Commands.json");
        singleReplicas = importSingleReplicas("src/main/resources/SimpleReplicas.json");
        randomReplicas = importRandomReplicas("src/main/resources/RandomReplicas.json");
    }

    private Map<Location, Map<String, String>> importCommands(String path) {

        Map<Location, Map<String, String>> map = new HashMap<>();

        Command[] commands = importFromJson(path, Command[].class);

        for (Command currentCommand : commands) {
            if (!map.containsKey(currentCommand.getLocation())) {
                map.put((currentCommand.getLocation()), new HashMap<>());
            }

            for (String variant : currentCommand.getInputVariants()) {
                map.get(currentCommand.getLocation()).put(variant, currentCommand.getId());
            }
        }

        return map;
    }

    private Map<String, String[]> importRandomReplicas(String path) {

        Map<String, String[]> map = new HashMap<>();

        ReplicaRandom[] quotes = importFromJson(path, ReplicaRandom[].class);

        for (ReplicaRandom currentQuote : quotes) {
            map.put(currentQuote.getName(), currentQuote.getResponseRandom());
        }

        return map;
    }

    private Map<String, String> importSingleReplicas(String path) {

        HashMap<String, String> map = new HashMap<>();

        ReplicaSimple[] quotes = importFromJson(path, ReplicaSimple[].class);

        for (ReplicaSimple currentQuote : quotes) {
            map.put(currentQuote.getName(), currentQuote.getResponse());
        }

        return map;
    }

    private <T> T[] importFromJson(String path, Class<T[]> clazz) {
        try {
            return mapper.readValue(new File(path), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Location, Map<String, String>> getCommands() {
        return commands;
    }

    public Map<String, String> getSingleReplicas() {
        return singleReplicas;
    }

    public Map<String, String[]> getRandomReplicas() {
        return randomReplicas;
    }
}
