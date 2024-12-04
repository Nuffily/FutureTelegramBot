import console_bot.Bot;
import console_bot.PrintService;
import console_bot.ResourceStorage;
import console_bot.TheoryService;
import model.Theory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.MyUtils;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TheoryServiceTest {

    private final ResourceStorage storage = new ResourceStorage();
    private final Bot bot = new Bot(storage);
    private final TheoryService theoryService = new TheoryService(storage, new PrintService());
    private final Theory theory = new Theory();

    @BeforeEach
    public void init(){

        MyUtils.isRandomFixed = true;
        bot.printer.consoleMode = false;
        bot.input.consoleMode = false;

        new Thread(bot).start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        bot.printer.que.remove();

    }


    @Test
    public void testDisplayAvailableTheories() {

        int countOfTheories = 0;
        int countOfNotTheories = 2;

        try {
            Field theories = theoryService.getClass().getDeclaredField("theories");
            theories.setAccessible(true);
            Theory[] value = (Theory[])theories.get(theoryService);
            for (Theory theory : value) {
                countOfTheories++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        bot.input.addToQueue("th");
        bot.printer.que.remove();
        bot.input.addToQueue("tt");

        assertEquals(countOfTheories, bot.printer.que.toString().split(",").length - countOfNotTheories);
    }


    @Test
    public void testDisplaySection() {

        int countOfSection = 0;
        int countOfNotSection = 2;

        try {
            Field theories = theoryService.getClass().getDeclaredField("theories");
            theories.setAccessible(true);
            Theory[] value = (Theory[])theories.get(theoryService);
            int i = 0;
            for (Theory.Section section : value[i++].getSections()) {
                countOfSection++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        bot.input.addToQueue("th");
        bot.printer.que.remove();
        bot.input.addToQueue("tt");
        bot.input.addToQueue("1");

        assertEquals(countOfSection, bot.printer.que.toString().split(",").length - countOfNotSection);
    }


}
