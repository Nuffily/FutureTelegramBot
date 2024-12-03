import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.MyUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PrintServiceTest {

    private final ResourceStorage storage = new ResourceStorage();
    private final PrintService testPrinter = new PrintService(storage);
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    public void init(){
        System.setOut(new PrintStream(outputStreamCaptor));
        MyUtils.isRandomFixed = true;
    }


    @Test
    public void testPrintlnResponse(){

        String testCommand = "unknownCommand";
        testPrinter.printlnResponse(testCommand);

        String[] testText = outputStreamCaptor.toString().split("\n");

        Assertions.assertEquals("'help' - главное лекарство от незнания\r", testText[0]);
    }


    @Test
    public void testGetQuote(){



    }


}
