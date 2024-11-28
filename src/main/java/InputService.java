import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class InputService {
    Scanner scanner = new Scanner(System.in);
    Queue<String> que = new LinkedList<>();

    public String getInput() {
        if (que.isEmpty()) {
            return scanner.nextLine();
        }
        else {
            return que.remove();
        }
    }

    public void addToQueue(String... messages) {
        Collections.addAll(que, messages);
    }
}
