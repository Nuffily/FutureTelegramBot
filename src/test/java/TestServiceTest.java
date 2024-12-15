import bot.console.Bot;
import bot.console.InputService;
import bot.console.OutputService;
import bot.console.ResourceStorage;
import bot.console.NonConsoleOutputService;
import bot.console.NonConsoleInputService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestServiceTest {

    private final ResourceStorage storage = new ResourceStorage();
    private final OutputService outputService = new NonConsoleOutputService(storage, new Random(1));
    private final InputService inputService = new NonConsoleInputService();
    private final Bot bot = new Bot(storage, outputService, inputService);

    @BeforeEach
    public void init() {
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
        assertEquals("Такого варианта ответа нет\n", bot.printer.getAllOutput());
        bot.input.addToQueue("0");
        assertEquals("Такого варианта ответа нет\n", bot.printer.getAllOutput());
        bot.input.addToQueue("-5");
        assertEquals("Такого варианта ответа нет\n", bot.printer.getAllOutput());

        bot.input.addToQueue("a");
        assertEquals("Ответ должен быть числом\n", bot.printer.getAllOutput());
        bot.input.addToQueue("?");
        assertEquals("Ответ должен быть числом\n", bot.printer.getAllOutput());
    }

    @Test
    public void testWrongAnswer() {
        bot.input.addToQueue("q");
        bot.printer.getAllOutput();

        bot.input.addToQueue("4");
        assertEquals("""
                Вообще ни разу, по новой
                Верным был вариант 3
                """, bot.printer.getAllOutput());
    }

    @Test
    public void testCorrectAnswer() {
        bot.input.addToQueue("q");
        bot.printer.getAllOutput();

        bot.input.addToQueue("3");
        assertEquals("Верно! Мужик\n", bot.printer.getAllOutput());
    }

    @Test
    public void testShowExplanationAfterMistake() {
        bot.input.addToQueue("js");
        bot.printer.getAllOutput();
        bot.input.addToQueue("explanation");
        assertEquals("Сначала попробуй пройти хоть один тест\n", bot.printer.getAllOutput());

        bot.input.addToQueue("q");
        bot.input.addToQueue("3");
        bot.printer.getAllOutput();

        bot.input.addToQueue("explanation");
        assertEquals("""
                Правильным ответом был вариант под номером 3 (4 10 18)
                -------------------------------
                Каждый шаг цикла увеличивает 'a' и добавляет к 'b' удвоенное значение 'a'. После трех итераций значения b будут: 4, 10, 18.
                """, bot.printer.getAllOutput());
    }

    @Test
    public void testShowExplanationOFF() {

        bot.input.addToQueue("settings");
        bot.input.addToQueue("showExplanation OFF");
        bot.input.addToQueue("back");
        bot.input.addToQueue("js");
        bot.input.addToQueue("q");
        bot.printer.getAllOutput();
        bot.input.addToQueue("2");


        assertEquals("Вообще ни разу, по новой\nВерным был вариант 3\n", bot.printer.getAllOutput());
    }

    @Test
    public void testShowExplanationON() {

        bot.input.addToQueue("settings");
        bot.input.addToQueue("showExplanation ON");
        bot.input.addToQueue("back");
        bot.input.addToQueue("js");
        bot.input.addToQueue("q");
        bot.printer.getAllOutput();
        bot.input.addToQueue("2");


        assertEquals("""
                        Вообще ни разу, по новой
                        Верным был вариант 3
                        -------------------------------
                        Массив содержит три элемента, поэтому его длина равна 3.
                        """, bot.printer.getAllOutput());
    }

    @Test
    public void testShowAnswerON() {

        bot.input.addToQueue("settings");
        bot.input.addToQueue("showAnswer ON");
        bot.input.addToQueue("back");
        bot.input.addToQueue("js");
        bot.input.addToQueue("q");
        bot.printer.getAllOutput();
        bot.input.addToQueue("2");


        assertEquals("""
                        Вообще ни разу, по новой
                        Верным был вариант 3
                        """, bot.printer.getAllOutput());
    }

    @Test
    public void testShowAnswerOFF() {

        bot.input.addToQueue("settings");
        bot.input.addToQueue("showAnswer OFF");
        bot.input.addToQueue("back");
        bot.input.addToQueue("js");
        bot.input.addToQueue("q");
        bot.printer.getAllOutput();
        bot.input.addToQueue("2");


        assertEquals("""
                        Вообще ни разу, по новой
                        """, bot.printer.getAllOutput());
    }

}