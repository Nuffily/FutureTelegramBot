import console_bot.Bot;
import console_bot.PrintService;
import console_bot.ResourceStorage;
import console_bot.TheoryService;
import model.Theory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.MyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class TheoryServiceTest {

    private final ResourceStorage storage = new ResourceStorage();
    private final Bot bot = new Bot(storage);
    private final TheoryService theoryService = new TheoryService(storage, new PrintService());

    @BeforeEach
    public void init(){

        MyUtils.isRandomFixed = true;


    }


    @Test
    public void testDisplayAvailableTheories() {

        int countOfTheories = 1;
        try {
            Field theories = theoryService.getClass().getDeclaredField("theories");
            theories.setAccessible(true);
            Theory[] value = (Theory[])theories.get(theoryService);
            for (Theory theory : value) {
                countOfTheories++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



//        try {
//            Method testedMethod = TheoryService.class.getDeclaredMethod("displayAvailableTheories");
//            testedMethod.setAccessible(true);
//            testedMethod.invoke(theoryService);
//            assertEquals(countOfTheories, );
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }


}
