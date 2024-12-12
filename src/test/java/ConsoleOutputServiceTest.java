//import bot.console.InputService;
//import bot.console.ConsoleOutputService;
//import bot.console.ResourceStorage;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import utils.MyUtils;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//import java.lang.reflect.Method;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//public class ConsoleOutputServiceTest {
//
//    private final ResourceStorage storage = new ResourceStorage();
//    private final ConsoleOutputService printer = new ConsoleOutputService(storage);
//    private final InputService input = new InputService();
//
//
//    @BeforeEach
//    public void init() {
//        MyUtils.random.setSeed(1);
//
//        printer.consoleMode = false;
//        input.consoleMode = false;
//    }
//
//
//    @Test
//    public void testPrintResponse() {
//
//        printer.printlnResponse("unknownCommand");
//        assertEquals("Человечество еще не изобрело такую команду\n", printer.getAllOutput());
//
//        printer.printResponse("correctAnswerIs");
//        assertEquals("Ответ - ", printer.getAllOutput());
//    }
//
//
//    @Test
//    public void testGetQuote() {
//
//        String testCommand = "saveStats";
//
//        try {
//            Method testGetQuoteMethod = ConsoleOutputService.class
//                    .getDeclaredMethod("getQuote", String.class);
//
//            testGetQuoteMethod.setAccessible(true);
//            assertEquals("Статистика сохранена! Чтобы загрузить ее при "
//                            + "следующем запуске, используй 'upload'",
//                    testGetQuoteMethod.invoke(printer, testCommand).toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Test
//    public void testPrintLnConsoleModeOn() {
//
//        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outputStreamCaptor));
//        var testQuote = 1 + "testData";
//
//        printer.consoleMode = true;
//        printer.println(testQuote);
//
//        assertEquals("1testData\n", outputStreamCaptor.toString());
//    }
//
//
//    @Test
//    public void testPrintLnConsoleModeOff() {
//
//        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outputStreamCaptor));
//        var testQuote = 1 + "testData";
//
//        printer.consoleMode = false;
//        printer.print(testQuote);
//
//        assertEquals("", outputStreamCaptor.toString());
//        assertEquals(testQuote, printer.getAllOutput());
//
//    }
//}