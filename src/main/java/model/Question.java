package model;

import java.util.Random;

public class Question {
    private String body;
    private String[] answers;
    private boolean[] isCorrect;
    private int number;
    private int countOfAnswers;
    private String explanation;

    public void shuffleAnswers(Random random) {

        for (int i = answers.length - 1; i > 0; i--) {
            int index = random.nextInt(i);

            String temp = answers[index];
            answers[index] = answers[i];
            answers[i] = temp;

            boolean temp2 = isCorrect[index];
            isCorrect[index] = isCorrect[i];
            isCorrect[i] = temp2;
        }
    }

    public int getCorrectAnswer() {
        for (int i = 0; ; i++) {
            if (isCorrect[i]) {
                return i + 1;
            }
        }
    }

    public String getStringTableOfAnswers() {
        String result = "";

        for (int i = 0; i < this.getAnswers().length; i++) {
            result += (i + 1) + ". " + this.getAnswers()[i] + "\n";
        }

        return result;
    }

    public String getBody() {
        return body;
    }

    public String[] getAnswers() {
        return answers;
    }

    public boolean[] getIsCorrect() {
        return isCorrect;
    }

    public int getNumber() {
        return number;
    }

    public int getCountOfAnswers() {
        return countOfAnswers;
    }

    public String getExplanation() {
        return explanation;
    }
}