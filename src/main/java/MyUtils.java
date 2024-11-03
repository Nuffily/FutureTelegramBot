public class MyUtils {

    static int getRandom(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }

    static <T> T getRandomElement(T[] arr) {
        int elem = getRandom(0, arr.length - 1);
        return arr[elem];
    }
}
