import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.MyUtils;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class BotTest {

    final ResourceStorage storage = new ResourceStorage();
    final Bot bot = new Bot(storage);
    final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    public void init(){
        System.setOut(new PrintStream(outputStreamCaptor));

        MyUtils.isRandomFixed = true;
    }


    @Test
    public void testRunNullCommand(){

        bot.input.addToQueue("null");
        bot.run();

        String[] a = outputStreamCaptor.toString().split("\n");

        Assertions.assertEquals("'help' - главное лекарство от незнания\r", a[0]);
    }


    @Test
    public void testRunKnownCommand(){

        bot.input.addToQueue("js");
        bot.run();

        String[] a = outputStreamCaptor.toString().split("\n");

        Assertions.assertEquals("Переходим к изучению JavaScript!\r" +
                "Вводи question для получения вопроса, ну или help, для всякого другого\r", a[0] + "\r" + a[1]);
    }


}