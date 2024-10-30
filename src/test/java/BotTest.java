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

//    @BeforeEach
//    public void setup() {
//        // Создаём заглушку для inputService
//        mockInputService = mock(InputService.class);
//
//        // Создаём и настраиваем объект OutputLibrary
//        library = new OutputLibrary();
//        library.commands = new HashMap<>();
//        library.randomQuotes = new HashMap<>();
//        library.singleQuotes = new HashMap<>();
//
//        // Настраиваем команду и ответ для /start
//        Map<String, String> mainCommands = new HashMap<>();
//        mainCommands.put("/start", "startCommand");
//        library.commands.put("main", mainCommands);
//
//        library.singleQuotes.put("startCommand", "Привет! Я бот.");
//
//        // Создаём бота и передаём ему моки
//        bot = new Bot();
//        bot.inputService = mockInputService;
//        bot.library = library;
//    }
//
//    @Test
//    public void testStartCommandOutput() {
//        // Настраиваем mock так, чтобы он возвращал "/start" при вызове inputService.readLine()
//        when(mockInputService.readLine()).thenReturn("/start");
//
//        // Перехватываем вывод в консоль
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//
//        // Запускаем метод run, который должен вызвать команду "/start"
//        bot.run();
//
//        // Проверяем, что в консоли был выведен ожидаемый результат
//        assertEquals("Привет! Я бот.\n", outContent.toString());
//
//        // Восстанавливаем стандартный вывод в консоль
//        System.setOut(System.out);
//    }

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
