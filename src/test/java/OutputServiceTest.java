import bot.console.OutputService;
import bot.console.ResourceStorage;
import bot.console.NonConsoleOutputService;
import bot.console.ConsoleOutputService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class OutputServiceTest {

    private final ResourceStorage storage = new ResourceStorage();
    private final OutputService nonPrinter = new NonConsoleOutputService(storage, new Random(1));
    private final OutputService printer = new ConsoleOutputService(storage, new Random(1));


    @BeforeEach
    public void init() {

    }


    @Test
    public void testPrintResponse() {

        nonPrinter.printlnResponse("unknownCommand");
        assertEquals("Человечество еще не изобрело такую команду\n", nonPrinter.getAllOutput());

        nonPrinter.printResponse("correctAnswerIs");
        assertEquals("Ответ - ", nonPrinter.getAllOutput());

        nonPrinter.printResponse("correctAnswerIs");
        assertEquals("Правильным ответом был ответ ", nonPrinter.getAllOutput());
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
    public void testPrintLn() {

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        var testQuote = 1 + "testData";

        printer.println(testQuote);
        nonPrinter.println(testQuote);

        assertEquals(testQuote + "\r\n", outputStreamCaptor.toString());
        assertEquals(testQuote + "\n", nonPrinter.getAllOutput());
    }


    @Test
    public void testPrint() {

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        var testQuote = 1 + "testData";

        printer.print(testQuote);
        nonPrinter.print(testQuote);

        assertEquals(testQuote, outputStreamCaptor.toString());
        assertEquals(testQuote, nonPrinter.getAllOutput());

    }
}