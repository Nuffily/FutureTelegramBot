import bot.console.*;
import model.Location;
import model.Theory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.MyUtils;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestServiceTest {

    private final ResourceStorage storage = new ResourceStorage();
    Bot bot = new Bot(storage);

    @BeforeEach
    public void init() {
        MyUtils.random.setSeed(1);

        bot.consoleModeDisable();

        new Thread(bot).start();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        bot.input.addToQueue("js");
        bot.printer.getAllOutput();
        bot.input.addToQueue("q");
    }

    @Test
    public void CorrectQuestionPrinting() {
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
    public void AnswerSuitabilityTest() {
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
    public void WrongAnswer() {
        bot.printer.getAllOutput();

        bot.input.addToQueue("4");
        assertEquals("Вообще ни разу, по новой\n", bot.printer.getOutput());
    }

    @Test
    public void CorrectAnswer() {
        bot.printer.getAllOutput();

        bot.input.addToQueue("3");
        assertEquals("Верно! Мужик\n", bot.printer.getOutput());
    }

}