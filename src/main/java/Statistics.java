public class Statistics {
    boolean[] questionPaseed = {};
    int CountOfPassedQuestions = 0;
    int[] questionsAttempts = {};
    int CountOfAttemptedQuestions = 0;

    public void printStats() {
        PrintService.println("Количество пройденных вопросов: " + CountOfPassedQuestions + "/" + questionPaseed.length);
        PrintService.println("Количество встретившихся вопросов: " + CountOfAttemptedQuestions + "/" + questionPaseed.length);
    }

    Statistics(ResourceStorage storage) {
        questionPaseed = new boolean[storage.JSQuestions.length + 1];
        questionsAttempts = new int[storage.JSQuestions.length + 1];
    }

    public void update(int numberOfQuestion, boolean result) {

        if (!questionPaseed[numberOfQuestion] && result) {
            questionPaseed[numberOfQuestion] = true;
            CountOfPassedQuestions++;
        }

        if (questionsAttempts[numberOfQuestion]++ == 0)
            CountOfAttemptedQuestions++;

    }
}
