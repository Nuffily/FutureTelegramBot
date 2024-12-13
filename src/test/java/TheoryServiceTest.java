import bot.console.Bot;
import bot.console.InputService;
import bot.console.OutputService;
import bot.console.ResourceStorage;
import bot.console.TheoryService;
import bot.console.NonConsoleOutputService;
import bot.console.NonConsoleInputService;
import bot.console.ConsoleOutputService;

import model.Theory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TheoryServiceTest {

    private final ResourceStorage storage = new ResourceStorage();
    private final OutputService outputService = new NonConsoleOutputService(storage, new Random(1));
    private final InputService inputService = new NonConsoleInputService();
    private final Bot bot = new Bot(storage, outputService, inputService);
    private final TheoryService theoryService =
            new TheoryService(new ConsoleOutputService(storage), new NonConsoleInputService());

    @BeforeEach
    public void init() {

        new Thread(bot).start();

        bot.input.addToQueue("js");
        bot.printer.getAllOutput();
    }

    @Test
    public void testDisplayAvailableTheories() {

        String expected = """
                Добро пожаловать в библеотеку теории по JavaScript!
                Доступные темы:
                """;

        try {
            Field theories = theoryService.getClass().getDeclaredField("theories");
            theories.setAccessible(true);
            Theory[] value = (Theory[]) theories.get(theoryService);
            for (int i = 0; i < value.length; i++) {
                expected += (i + 1) + ". " + value[i].getTitle()
                        + " - " + value[i].getCommand() + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        expected += "Введите номер темы или 'exit' для выхода: ";

        bot.input.addToQueue("t");

        assertEquals(expected, bot.printer.getAllOutput());
    }

    @Test
    public void testDisplaySection() {


        String expected = "Доступные разделы:\n";

        try {
            Field theories = theoryService.getClass().getDeclaredField("theories");
            theories.setAccessible(true);
            Theory[] value = (Theory[]) theories.get(theoryService);

            List<Theory.Section> sections = value[1].getSections();
            for (int i = 0; i < sections.size(); i++) {
                expected += (i + 1) + ". " + sections.get(i).getTitle() + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        expected += "Введите номер раздела для изучения или 'back' для возврата: ";

        bot.input.addToQueue("t");
        bot.printer.getAllOutput();

        bot.input.addToQueue("2");

        assertEquals(expected, bot.printer.getAllOutput());
    }

    @Test
    public void testDisplayContent() {

        String expected = "";

        try {
            Field theories = theoryService.getClass().getDeclaredField("theories");
            theories.setAccessible(true);
            Theory[] value = (Theory[]) theories.get(theoryService);

            expected = value[1].getSections().getFirst().getContent() + "\n";

        } catch (Exception e) {
            e.printStackTrace();
        }

        expected += "Введите back для возврата.\n";

        bot.input.addToQueue("t");
        bot.input.addToQueue("2");

        bot.printer.getAllOutput();

        bot.input.addToQueue("1");

        assertEquals(expected, bot.printer.getAllOutput());
    }
}