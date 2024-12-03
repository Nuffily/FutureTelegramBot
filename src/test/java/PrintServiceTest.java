import console_bot.Bot;
import console_bot.PrintService;
import console_bot.ResourceStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.MyUtils;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PrintServiceTest {

    private final ResourceStorage storage = new ResourceStorage();
    private final Bot bot = new Bot(storage);
    private  final PrintService printService = new PrintService(storage);


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
    public void testPrintlnResponse(){

        String testCommand = "unknownCommand";
        bot.printer.printlnResponse(testCommand);

        bot.input.addToQueue("null");

        assertEquals("'help' - главное лекарство от незнания", bot.printer.que.remove());
    }


    @Test
    public void testGetQuote(){

        String testCommand = "saveStats";

        try {
            Method testGetQuoteMethod = PrintService.class.getDeclaredMethod("getQuote", String.class);

            testGetQuoteMethod.setAccessible(true);
            assertEquals("Статистика сохранена! Чтобы загрузить ее при следующем запуске, используй 'upload'",
                    testGetQuoteMethod.invoke(printService, testCommand).toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void testPrintLnConsoleModeOn(){

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        var testQuote = 1 + "testData";

        bot.printer.consoleMode = true;
        bot.printer.println(testQuote);

        assertEquals("1testData\r\n", outputStreamCaptor.toString());
    }


    @Test
    public void testPrintLnConsoleModeOff(){

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        var testQuote = 1 + "testData";

        bot.printer.consoleMode = false;
        bot.printer.println(testQuote);

        assertEquals("", outputStreamCaptor.toString());
        assertEquals(testQuote, bot.printer.que.remove());

    }
}
