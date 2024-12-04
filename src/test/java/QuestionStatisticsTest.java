import com.fasterxml.jackson.databind.ObjectMapper;
import console_bot.OutputService;
import console_bot.QuestionStatistics;
import model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionStatisticsTest {

    Question[] question = new Question[10];
    QuestionStatistics statistics = new QuestionStatistics(question, new OutputService());

    @BeforeEach
    public void init() {
        statistics.updateStats(1, true);
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

        statistics.updateStats(2, false);

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
            String testContent = new String(Files.readAllBytes(Path.of(testPath)));
            ObjectMapper objectMapper = new ObjectMapper();

//            QuestionStatistics statisticsCopy = objectMapper.readTree(testContent));
            QuestionStatistics statisticsCopy = objectMapper.readValue(new File(testPath), QuestionStatistics.class);

            statisticsCopy.pr(new OutputService());
            statisticsCopy.printStats();
            statistics.printStats();

            assertTrue(statistics.equals(statisticsCopy));

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
        statistics.uploadStats(testPath);
        assertTrue(Files.exists(Path.of(testPath)));

        try {

            String testContent = new String(Files.readAllBytes(Path.of(testPath)));
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(testContent);

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


}
