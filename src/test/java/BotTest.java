import bot.console.Bot;
import bot.console.InputService;
import bot.console.OutputService;
import bot.console.ResourceStorage;
import bot.console.NonConsoleOutputService;
import bot.console.NonConsoleInputService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;


public class BotTest {

    private final ResourceStorage storage = new ResourceStorage();
    private final OutputService outputService = new NonConsoleOutputService(storage, new Random(1));
    private final InputService inputService = new NonConsoleInputService();
    private final Bot bot = new Bot(storage, outputService, inputService, -1);


    @BeforeEach
    public void init() {

        new Thread(bot).start();

        bot.printer.getAllOutput();
    }


    @Test
    public void testRunUnknownCommand() {

        bot.input.addToQueue("null");

        Assertions.assertEquals("Человечество еще не изобрело такую команду\n", bot.printer.getAllOutput());

        bot.input.addToQueue("qwfrgreg");
        Assertions.assertEquals("Человечество еще не изобрело такую команду\n", bot.printer.getAllOutput());

        bot.input.addToQueue("1");
        Assertions.assertEquals("Лучше попробуй 'help'\n", bot.printer.getAllOutput());
    }


    @Test
    public void testRunKnownCommand() {

        bot.input.addToQueue("js");

        Assertions.assertEquals("""
                Переходим к изучению JavaScript!
                Вводи question для получения вопроса, ну или help, для всякого другого
                """, bot.printer.getAllOutput());

        bot.input.addToQueue("help");

        Assertions.assertEquals("""
                Это меню обучения ДжаваСкрипту
                Команды:
                quest - чтобы получить вопрос с вариантами ответов
                stats - посмотреть свою статистику по вопросам
                reset - сбросить статистику
                save - сохранить свою статистику
                upload - загрузить последнюю сохраненую статистику
                t - страница теории
                back - чтобы вернуться в главное меню
                """, bot.printer.getAllOutput());

        bot.input.addToQueue("back");

        Assertions.assertEquals("Назад в меню!\n", bot.printer.getAllOutput());

        bot.input.addToQueue("help");

        Assertions.assertEquals("""
                Это учебный бот. Пока тут есть только тесты для Джаваскрипта и чуть математики
                Команды:
                JS - для перехода в меню обучения ДжаваСкрипту
                Math - для перехода в меню обучения Математики
                Settings - для перехода в меню настроек
                exit - чтобы прервать работу программы
                """, bot.printer.getAllOutput());
    }

}