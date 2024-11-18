package utils;

import java.util.Random;

public class MyUtils {

    public static int getRandom(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }

    public static <T> T getRandomElement(T[] arr) {
        int elem = getRandom(0, arr.length - 1);
        return arr[elem];
    }

    public static <T> void shuffleArray(T[] arr) {
        Random rnd = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            T temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
    }
}