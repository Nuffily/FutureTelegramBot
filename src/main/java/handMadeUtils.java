import java.util.Random;

public class handMadeUtils {

    static <T> void shuffleArray(T[] arr) {
        Random rnd = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            T temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
    }

    static int getRandom(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }

    static <T> T getRndElem(T[] arr) {
        int elem = getRandom(0, arr.length - 1);
        return arr[elem];
    }

    //1001 + (int) (Math.random() * (1002 - 1001 + 1))


}
