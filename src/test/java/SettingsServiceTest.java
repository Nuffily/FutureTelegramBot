import bot.console.Bot;
import bot.console.ResourceStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SettingsServiceTest {
    private final ResourceStorage storage = new ResourceStorage();
    private final Bot bot = new Bot(storage);


    @BeforeEach
    public void init() {
        bot.consoleModeDisable();

        new Thread(bot).start();
        bot.input.addToQueue("settings");

        bot.printer.getAllOutput();
    }


    @Test
    public void testChangeSettings() {
        bot.input.addToQueue("repeat OFF");
        assertEquals("встречавшиеся вопросы скрыты\n", bot.printer.getOutput());

        bot.input.addToQueue("shwexp on");
        assertEquals("при неверном решении выводится объяснение\n", bot.printer.getOutput());

        bot.input.addToQueue("b");
        bot.printer.getOutput();

        bot.input.addToQueue("settings");
        assertEquals("""
                Это меню настроек. Команды:
                1. repeat (OFF) - выводить/скрыть уже встречавшиеся вопросы
                2. repeatSolved (ON) - выводить/скрыть верно решенные вопросы
                3. showAnswer (ON) - выводить/не выводить ответы при неверном ответе на вопрос
                4. showExplanation (ON) - выводить/не выводить объяснение ответа при неверном ответе на вопрос
                """, bot.printer.getAllOutput());

    }
}
