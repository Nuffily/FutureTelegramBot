import console_bot.*;
import model.Theory;
import org.junit.Test;
import static org.mockito.Mockito.*; // Для статических методов

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito; // Для общих методов
import utils.MyUtils;

public class TheoryTest {

    InputService mockInput = Mockito.mock(InputService.class);
    OutputService mockOutput = Mockito.mock(OutputService.class);
    TheoryService service = new TheoryService(mockOutput, mockInput);  // Предположим, что у вас есть такой сервис

//    private final ResourceStorage storage = new ResourceStorage();
//    private final Bot bot = new Bot(storage);
//


    @Test
    public void testStartTheoryWithRand() {
        when(mockInput.getInput()).thenReturn("rand", "back");
        service.startTheory();

        verify(mockOutput).println(contains("---- Случайная теория ----"));
        verify(mockOutput).println("Выход из случайной теории.");

        when(mockInput.getInput()).thenReturn("rand", "back");
        verify(mockOutput).println(contains("--------------------------"));
        verify(mockOutput, atLeastOnce()).println(contains("Вы в меню теории! \nВведите rand для получения рандомной теории, tstat для статистики или tt для списка теорий."));

    }


    @Test
    public void testStartTheoryWithGettingTheory() {
        // Генерация случайной теории и раздела
        int testRandTheory = MyUtils.getRandom(1, service.getTheories().length - 1);
        Theory randTheory = service.getTheories()[testRandTheory];
        int testRandSection = MyUtils.getRandom(0, randTheory.getSections().size() - 1);

        when(mockInput.getInput()).thenReturn("tt", String.valueOf(testRandTheory),  "back");
        service.handleUserInputForTheory();
        verify(mockOutput).println(contains("Доступные темы:"));
        verify(mockOutput).println(contains("Доступные разделы:"));
        verify(mockOutput).println(contains("Выходим из раздела"));

    }

}
