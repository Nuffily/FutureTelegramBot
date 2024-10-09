import java.io.BufferedReader;
import java.util.Random;

public class some {
    static void shuffleArray(int[] arr) {
        Random rnd = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
    }

    static int getRandom(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }

    //1001 + (int) (Math.random() * (1002 - 1001 + 1))


}
