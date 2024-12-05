import static org.mockito.Mockito.*;

import console_bot.OutputService;
import console_bot.ResourceStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OutputServiceTest {

    private ResourceStorage mockStorage;
    private OutputService outputService;

    @BeforeEach
    public void setUp() {
        mockStorage = mock(ResourceStorage.class);
        outputService = new OutputService(mockStorage);
    }

    @Test
    public void testPrintlnResponse() {
        String command = "greet";
        String expectedQuote = "Hello, world!";

        when(mockStorage.getSingleReplicas()).thenReturn(Map.of(command, expectedQuote));

        outputService.printlnResponse(command);

        verify(mockStorage).getSingleReplicas();
    }

    @Test
    public void testConsoleMode() {
        ResourceStorage mockStorage = mock(ResourceStorage.class);
        OutputService outputService = new OutputService(mockStorage);

        outputService.consoleMode = true;
        outputService.println("Test");
        verify(mockStorage, never()).getSingleReplicas();
    }

    @Test
    public void testQueueMode() {
        ResourceStorage mockStorage = mock(ResourceStorage.class);
        OutputService outputService = new OutputService(mockStorage);

        outputService.consoleMode = false;

        outputService.println("Test");

        assertEquals("Test\n", outputService.getOutput());
    }

    @Test
    public void testGetAllOutput() {
        ResourceStorage mockStorage = mock(ResourceStorage.class);
        OutputService outputService = new OutputService(mockStorage);

        outputService.consoleMode = false;

        outputService.print("Test 1");
        outputService.print("Test 2");
        assertEquals("Test 1Test 2", outputService.getAllOutput());
    }

}
