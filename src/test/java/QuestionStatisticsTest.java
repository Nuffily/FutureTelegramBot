import com.fasterxml.jackson.databind.ObjectMapper;
import console_bot.OutputService;
import console_bot.QuestionStatistics;
import console_bot.ResourceStorage;
import model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionStatisticsTest {

    ResourceStorage storage = new ResourceStorage();
    Question[] question = new Question[10];
    OutputService printer = new OutputService(storage);
    QuestionStatistics statistics = new QuestionStatistics(question, printer);


    @BeforeEach
    public void init() {
        printer.consoleMode = false;

        statistics.updateStats(1, true);
        statistics.updateStats(2, false);
        statistics.updateStats(2, false);
    }

    @Test
    public void testUpdateStatsPassing() {

        assertTrue(statistics.getQuestionPassed()[1]);
        assertFalse(statistics.getQuestionPassed()[2]);

        assertEquals(1, statistics.getCountOfPassedQuestions());
    }


    @Test
    public void testUpdateStatsAttempts() {

        assertEquals(1, statistics.getQuestionsAttempts()[1]);
        assertEquals(2, statistics.getQuestionsAttempts()[2]);

        assertEquals(2, statistics.getCountOfAttemptedQuestions());
    }


    @Test
    public void testSaveStats() {

        String testPath = "test.json";
        statistics.saveStats(testPath);

        assertTrue(Files.exists(Path.of(testPath)));

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            QuestionStatistics statisticsCopy = objectMapper.readValue(new File(testPath), QuestionStatistics.class);

            assertArrayEquals(statistics.getQuestionPassed(), statisticsCopy.getQuestionPassed());
            assertArrayEquals(statistics.getQuestionsAttempts(), statisticsCopy.getQuestionsAttempts());
            assertEquals(statistics.getCountOfAttemptedQuestions(), statisticsCopy.getCountOfAttemptedQuestions());
            assertEquals(statistics.getCountOfPassedQuestions(), statisticsCopy.getCountOfPassedQuestions());

        } catch (IOException e) {

            fail("Содержимое файла не является валидным JSON: " + e.getMessage());

        } finally {
            try {
                Files.delete(Path.of(testPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testUploadStats() {

        String testPath = "test.json";
        statistics.saveStats(testPath);

        QuestionStatistics statisticsCopy = new QuestionStatistics(question, printer);
        statisticsCopy.uploadStats(testPath);

        assertTrue(Files.exists(Path.of(testPath)));
        assertEquals(printer.getOutput(), "Статистика загружена! Чтобы взгянуть на нее, напиши 'stats'\n");

        assertArrayEquals(statistics.getQuestionPassed(), statisticsCopy.getQuestionPassed());
        assertArrayEquals(statistics.getQuestionsAttempts(), statisticsCopy.getQuestionsAttempts());
        assertEquals(statistics.getCountOfAttemptedQuestions(), statisticsCopy.getCountOfAttemptedQuestions());
        assertEquals(statistics.getCountOfPassedQuestions(), statisticsCopy.getCountOfPassedQuestions());
    }

    @Test
    public void testUploadStatsGarbage() {

        String testPath = "test.json";
        try (FileWriter writer = new FileWriter(testPath, false)) {
            String text = "Hello Gold!";
            writer.write("""
                {
                    "questionPassed" : [ false, true, false, false, false, false, false, false, false, false, false ],
                    "countOfPassedQuestions" : 1,
                    "questionsAttempts" : [ 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0 ],
                    "countOfAttemptedQuestions" : 2     GARBAGE <-------
                }
            """);
            writer.flush();

            QuestionStatistics statisticsCopy = new QuestionStatistics(question, printer);
            statisticsCopy.uploadStats(testPath);

            assertEquals(printer.getOutput(), "Существующая статистика не найдена или повреждена\n");
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        } finally {
            try {
                Files.delete(Path.of(testPath));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


}