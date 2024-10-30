import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;

public class BotTest {
    private Bot bot;
    private InputService mockInputService;
    private OutputLibrary library;

    @Test
    public void testExecute_travelToJS() {
        Bot bot = new Bot();
        bot.execute("travelToJS");
        assertEquals("js", bot.location);
    }

    @Test
    public void testExecute_toMenu() {
        Bot bot = new Bot();
        bot.execute("toMenu");
        assertEquals("main", bot.location);
    }

    @Test
    public void testExecute_exit() {
        Bot bot = new Bot();
        bot.execute("exit");
        assertEquals("exit", bot.location);
    }

    @Test
    public void testStartCommandOutput() {
        // Перехватываем вывод
        Bot bot = new Bot();
        bot.run();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));


        // Проверяем, что было выведено на консоль
        assertEquals("Привет! Я бот.\n", outContent.toString());

        // Восстанавливаем стандартный вывод
        System.setOut(System.out);
    }


}
