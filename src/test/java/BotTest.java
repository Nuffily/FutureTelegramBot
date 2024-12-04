import bot.console.Bot;
import bot.console.ResourceStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.MyUtils;


public class BotTest {

    private final ResourceStorage storage = new ResourceStorage();
    private final Bot bot = new Bot(storage);


    @BeforeEach
    public void init(){
        MyUtils.isRandomFixed = true;

        bot.printer.consoleMode = false;
        bot.input.consoleMode = false;

        new Thread(bot).start();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        bot.printer.getOutput();
    }


    @Test
    public void testRunUnknownCommand(){

        bot.input.addToQueue("null");
        Assertions.assertEquals("'help' - главное лекарство от незнания\n", bot.printer.getOutput());

        bot.input.addToQueue("qwfrgreg");
        Assertions.assertEquals("'help' - главное лекарство от незнания\n", bot.printer.getOutput());

        bot.input.addToQueue("1");
        Assertions.assertEquals("'help' - главное лекарство от незнания\n", bot.printer.getOutput());
    }


    @Test
    public void testRunKnownCommand(){

        bot.input.addToQueue("js");

        Assertions.assertEquals("""
                Переходим к изучению JavaScript!
                Вводи question для получения вопроса, ну или help, для всякого другого
                """, bot.printer.getOutput());

        bot.input.addToQueue("help");

        Assertions.assertEquals("""
                Это меню обучения ДжаваСкрипту
                Команды:
                quest - чтобы получить вопрос с вариантами ответов
                stats - посмотреть свою статистику по вопросам
                save - сохранить свою статистику
                upload - загрузить последнюю сохраненую статистику
                t - страница теории
                back - чтобы вернуться в главное меню
                """, bot.printer.getOutput());

        bot.input.addToQueue("back");

        Assertions.assertEquals("Назад в меню!\n", bot.printer.getOutput());

        bot.input.addToQueue("help");

        Assertions.assertEquals("""
                Это учебный бот!!! Пока тут есть только тесты для Джаваскрипта
                Команды:
                JS - для перехода в меню обучения ДжаваСкрипту
                Math - для перехода в меню обучения Математики
                exit - чтобы прервать работу программы
                """, bot.printer.getOutput());
    }


}