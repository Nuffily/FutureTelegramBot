package bot.telegram.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BotConfig {

    String botName;
    String token;

    public BotConfig() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("src/main/resources/application.properties"));

            botName = reader.readLine();
            token = reader.readLine();

            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String getBotName() {
        return botName;
    }

    public String getToken() {
        return token;
    }
}
