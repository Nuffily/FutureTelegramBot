package utils;

import java.util.Random;

public class MyUtils {
    static public boolean isRandomFixed = false;

    public static int getRandom(int min, int max) {
        if (!isRandomFixed)
            return min + (int) (Math.random() * (max - min + 1));
        else
            return min;
    }

    public static <T> T getRandomElement(T[] arr) {
        int elem = getRandom(0, arr.length - 1);
        return arr[elem];
    }
}
