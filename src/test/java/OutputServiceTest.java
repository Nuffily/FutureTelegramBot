import bot.console.InputService;
import bot.console.OutputService;
import bot.console.ResourceStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.MyUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class OutputServiceTest {

    private final ResourceStorage storage = new ResourceStorage();
    private final OutputService printer = new OutputService(storage);
    private final InputService input = new InputService();


    @BeforeEach
    public void init() {
        MyUtils.random.setSeed(1);

        printer.consoleMode = false;
        input.consoleMode = false;
    }


    @Test
    public void testPrintResponse() {

        printer.printlnResponse("unknownCommand");
        assertEquals("Человечество еще не изобрело такую команду\n", printer.getOutput());

        printer.printResponse("correctAnswerIs");
        assertEquals("Ответ - ", printer.getOutput());
    }


    @Test
    public void testGetQuote() {

        String testCommand = "saveStats";

        try {
            Method testGetQuoteMethod = OutputService.class
                    .getDeclaredMethod("getQuote", String.class);

            testGetQuoteMethod.setAccessible(true);
            assertEquals("Статистика сохранена! Чтобы загрузить ее при "
                            + "следующем запуске, используй 'upload'",
                    testGetQuoteMethod.invoke(printer, testCommand).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testPrintLnConsoleModeOn() {

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        var testQuote = 1 + "testData";

        printer.consoleMode = true;
        printer.println(testQuote);

        assertEquals("1testData\r\n", outputStreamCaptor.toString());
    }


    @Test
    public void testPrintLnConsoleModeOff() {

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        var testQuote = 1 + "testData";

        printer.consoleMode = false;
        printer.print(testQuote);

        assertEquals("", outputStreamCaptor.toString());
        assertEquals(testQuote, printer.getOutput());

    }
}