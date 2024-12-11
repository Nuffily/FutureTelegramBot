import bot.console.Bot;
import bot.console.ResourceStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.MyUtils;



import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestServiceTest {

    private final ResourceStorage storage = new ResourceStorage();
    Bot bot = new Bot(storage);

    @BeforeEach
    public void init() {
        MyUtils.random.setSeed(1);

        bot.consoleModeDisable();

        new Thread(bot).start();

        bot.input.addToQueue("js");
        bot.printer.getAllOutput();
    }

    @Test
    public void testCorrectQuestionPrinting() {
        bot.input.addToQueue("q");

        assertEquals("""
                Что выведет этот код?
                const arr = ['apple', 'banana', 'cherry'];
                console.log(arr.length);
                -------------------------------
                Варианты ответа:
                1. TypeError
                2. undefined
                3. 3
                4. 'apple'
                """, bot.printer.getAllOutput());
    }

    @Test
    public void testAnswerSuitabilityTest() {
        bot.input.addToQueue("q");
        bot.printer.getAllOutput();

        bot.input.addToQueue("5");
        assertEquals("Такого варианта ответа нет\n", bot.printer.getOutput());
        bot.input.addToQueue("0");
        assertEquals("Такого варианта ответа нет\n", bot.printer.getOutput());
        bot.input.addToQueue("-5");
        assertEquals("Такого варианта ответа нет\n", bot.printer.getOutput());

        bot.input.addToQueue("a");
        assertEquals("Ответ должен быть числом\n", bot.printer.getOutput());
        bot.input.addToQueue("?");
        assertEquals("Ответ должен быть числом\n", bot.printer.getOutput());
    }

    @Test
    public void testWrongAnswer() {
        bot.input.addToQueue("q");
        bot.printer.getAllOutput();

        bot.input.addToQueue("4");
        assertEquals("Вообще ни разу, по новой\n", bot.printer.getOutput());
    }

    @Test
    public void testCorrectAnswer() {
        bot.input.addToQueue("q");
        bot.printer.getAllOutput();

        bot.input.addToQueue("3");
        assertEquals("Верно! Мужик\n", bot.printer.getOutput());
    }

    @Test
    public void testShowExplanation() {
        bot.input.addToQueue("explanation");
        assertEquals("Сначала попробуй пройти хоть один тест\n", bot.printer.getOutput());

        bot.input.addToQueue("q");
        bot.input.addToQueue("3");
        bot.printer.getAllOutput();

        bot.input.addToQueue("explanation");
        assertEquals("""
                Верным был вариант 3 (3)
                -------------------------------
                Массив содержит три элемента, поэтому его длина равна 3.
                """, bot.printer.getAllOutput());
    }

}