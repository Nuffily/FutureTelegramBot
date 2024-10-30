//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import static org.mockito.Mockito.*;
//
//public class BotIntegrationTest {
//
//    @Test
//    public void testRun_withTravelToJSCommand() {
//        Bot bot = new Bot();
//        bot.library = new OutputLibrary();  // Подключаем тестовую библиотеку
//
//        // Мокаем inputService и задаём поведение для чтения команды
//        InputService mockInputService = Mockito.mock(InputService.class);
//        when(mockInputService.readLine()).thenReturn("travelToJS");
//
//        bot.inputService = mockInputService;
//        bot.run();
//
//        // Проверяем, что команда изменяет location на "js"
//        assertEquals("js", bot.location);
//    }
//}
