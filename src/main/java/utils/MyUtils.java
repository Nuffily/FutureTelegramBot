package utils;

import java.util.Random;

public class MyUtils {
    public static final Random random = new Random();

    public static int getRandom(int min, int max) {
        try {
            return min + random.nextInt(max);
        } catch (IllegalArgumentException e) {
            return min;
        }
    }

    public static <T> T getRandomElement(T[] arr) {
        int elem = random.nextInt(arr.length);
        return arr[elem];
    }
}
