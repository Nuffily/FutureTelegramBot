import bot.console.Bot;
import bot.console.OutputService;
import bot.console.ResourceStorage;
import bot.console.TheoryService;
import bot.console.InputService;
import model.Theory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.MyUtils;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TheoryServiceTest {

    private final ResourceStorage storage = new ResourceStorage();
    private final Bot bot = new Bot(storage);
    private final TheoryService theoryService =
            new TheoryService(new OutputService(storage), new InputService());

    @BeforeEach
    public void init() {

        MyUtils.random.setSeed(1);
        bot.consoleModeDisable();

        new Thread(bot).start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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


}