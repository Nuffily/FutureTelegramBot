package bot.console;

public interface InputService {
    String getInput();

    void addToQueue(String... messages);
}