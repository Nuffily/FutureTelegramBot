package utils;

import java.util.Random;

public class MyUtils {
    static public final Random random = new Random();

    public static int getRandom(int min, int max) {
        return min + random.nextInt(max);
    }

    public static <T> T getRandomElement(T[] arr) {
        int elem = random.nextInt(arr.length);
        return arr[elem];
    }
}
