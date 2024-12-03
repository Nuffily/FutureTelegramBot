import console_bot.Bot;
import console_bot.ResourceStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.MyUtils;


public class BotTest {

    private final ResourceStorage storage = new ResourceStorage();
    private final Bot bot = new Bot(storage);


    @BeforeEach
    public void init(){
        MyUtils.isRandomFixed = true;

        bot.printer.consoleMode = false;
        bot.input.consoleMode = false;

        new Thread(bot).start();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        bot.printer.que.remove();
    }


    @Test
    public void testRunNullCommand(){

        bot.input.addToQueue("null");

        Assertions.assertEquals("'help' - главное лекарство от незнания", bot.printer.que.remove());
    }


    @Test
    public void testRunKnownCommand(){

        bot.input.addToQueue("js");

        Assertions.assertEquals("Переходим к изучению JavaScript!\n" +
                "Вводи question для получения вопроса, ну или help, для всякого другого", bot.printer.que.remove());
    }


}