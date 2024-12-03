import console_bot.PrintService;
import console_bot.QuestionStatistics;
import model.Location;
import model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionStatisticsTest {

    Question[] question = new Question[10];
    QuestionStatistics statistics = new QuestionStatistics(question, new PrintService());

    @BeforeEach
    public void init() {
        statistics.updateStats(1, true);
        statistics.updateStats(2, false);
    }

    @Test
    public void testUpdateStatsRightAnswer() {

        assertTrue(statistics.getQuestionPassed()[1]);
        assertFalse(statistics.getQuestionPassed()[2]);

        assertEquals(1, statistics.getQuestionsAttempts()[1]);
        assertEquals(1, statistics.getQuestionsAttempts()[2]);

        assertEquals(2, statistics.getCountOfAttemptedQuestions());
        assertEquals(1, statistics.getCountOfPassedQuestions());
    }


//    @Test
//    public void testUpdateStatsRightAnswer() {
//
//        boolean rightAnswer = true;
//        int numberOfQuestion = 1;
//
//        statistics.updateStats(1, rightAnswer);
//        assertTrue(statistics.getQuestionPassed()[numberOfQuestion]);
//        assertEquals(1, statistics.getCountOfAttemptedQuestions());
//        assertEquals(1, statistics.getCountOfPassedQuestions());
//        assertEquals(1, statistics.getQuestionsAttempts()[numberOfQuestion]);
//    }
//
//
//    @Test
//    public void testUpdateStatsWrongAnswer() {
//
//        boolean wrongAnswer = false;
//        int numberOfQuestion = 1;
//
//        statistics.updateStats(1, wrongAnswer);
//        assertFalse(statistics.getQuestionPassed()[numberOfQuestion]);
//        assertEquals(1, statistics.getCountOfAttemptedQuestions());
//        assertEquals(0, statistics.getCountOfPassedQuestions() );
//        assertEquals(1, statistics.getQuestionsAttempts()[numberOfQuestion]);
//    }
//
//
//    @Test
//    public void testSaveStats() {
//
//        String testPath = "test.json";
//        statistics.saveStats(testPath);
//        assertTrue(Files.exists(Path.of(testPath)));
//
//        try {
//
//            String testContent = new String(Files.readAllBytes(Path.of(testPath)));
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.readTree(testContent);
//
//        } catch (IOException e) {
//
//            fail("Содержимое файла не является валидным JSON: " + e.getMessage());
//
//        } finally {
//            try {
//                Files.delete(Path.of(testPath));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


}
