import bot.console.OutputService;
import bot.console.QuestionStatistics;
import bot.console.ResourceStorage;
import bot.console.NonConsoleOutputService;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Location;
import model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class QuestionStatisticsTest {

    ResourceStorage storage = new ResourceStorage();
    Question[] question = new Question[9];
    private final OutputService printer = new NonConsoleOutputService(storage, new Random(1));
    QuestionStatistics statistics = new QuestionStatistics(question, printer);


    @BeforeEach
    public void init() {

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

        String testPath = "src/test/";
        statistics.saveStats(testPath, Location.JS);

        assertTrue(Files.exists(Path.of(testPath)));

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            QuestionStatistics statisticsCopy = objectMapper
                    .readValue(new File(testPath + "StatisticsJS.json"), QuestionStatistics.class);

            assertArrayEquals(statistics.getQuestionPassed(),
                    statisticsCopy.getQuestionPassed());
            assertArrayEquals(statistics.getQuestionsAttempts(),
                    statisticsCopy.getQuestionsAttempts());
            assertEquals(statistics.getCountOfAttemptedQuestions(),
                    statisticsCopy.getCountOfAttemptedQuestions());
            assertEquals(statistics.getCountOfPassedQuestions(),
                    statisticsCopy.getCountOfPassedQuestions());

        } catch (IOException e) {

            fail("Содержимое файла не является валидным JSON: " + e.getMessage());

        } finally {
            try {
                Files.delete(Path.of("src/test/StatisticsJS.json"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Test
    public void testUploadStats() {

        String testPath = "src/test/";
        statistics.saveStats(testPath, Location.JS);

        QuestionStatistics statisticsCopy = new QuestionStatistics(question, printer);

        try {
            statisticsCopy.uploadStats(testPath + "StatisticsJS.json");

            assertTrue(Files.exists(Path.of(testPath)));

            assertArrayEquals(statistics.getQuestionPassed(),
                    statisticsCopy.getQuestionPassed());
            assertArrayEquals(statistics.getQuestionsAttempts(),
                    statisticsCopy.getQuestionsAttempts());
            assertEquals(statistics.getCountOfAttemptedQuestions(),
                    statisticsCopy.getCountOfAttemptedQuestions());
            assertEquals(statistics.getCountOfPassedQuestions(),
                    statisticsCopy.getCountOfPassedQuestions());

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                Files.delete(Path.of("src/test/StatisticsJS.json"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Test
    public void testUploadStatsGarbage() {

        String testPath = "test.json";
        try (FileWriter writer = new FileWriter(testPath, false)) {
            writer.write("""
                    {
                        "questionPassed" : [ false, true, false, false, false, false, false, false, false, false],
                        "countOfPassedQuestions" : 1,
                        "questionsAttempts" : [ 0, 1, 2, 0, 0, 0, 0, 0, 0, 0],
                        "countOfAttemptedQuestions" : 2     GARBAGE <-------
                    }
                    """);
            writer.flush();

            QuestionStatistics statisticsCopy = new QuestionStatistics(question, printer);

            Exception exception = new Exception();

            try {
                statisticsCopy.uploadStats(testPath);
            } catch (Exception e) {
                exception = e;
            }

            assertEquals("Unexpected character ('G' (code 71)): was expecting comma to separate " +
                    "Object entries\n" +
                    " at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); " +
                    "line: 5, column: 41]", exception.getMessage());

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                Files.delete(Path.of(testPath));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Test
    public void testPrintStats() {
        statistics.printStats();

        assertEquals("""
                Количество пройденных вопросов: 1/9
                Количество встретившихся вопросов: 2/9
                """, printer.getAllOutput());

        statistics.updateStats(4, false);
        statistics.updateStats(6, true);

        statistics.printStats();

        assertEquals("""
                Количество пройденных вопросов: 2/9
                Количество встретившихся вопросов: 4/9
                """, printer.getAllOutput());
    }


}
