package model;

import java.util.Random;


public class Question {
    private String body;
    private String[] answers;
    private boolean[] isCorrect;
    private int number;
    private int countOfAnswers;
    private String explanation; // в процессе

    public void shuffleAnswers() {
        Random rnd = new Random();
        for (int i = answers.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String temp = answers[index];
            answers[index] = answers[i];
            answers[i] = temp;
            boolean temp2 = isCorrect[index];
            isCorrect[index] = isCorrect[i];
            isCorrect[i] = temp2;
        }
    }

    public int getCorrectAnswer() {
        for (int i = 0;; i++) {
            if (isCorrect[i]) {
                return i + 1;
            }
        }
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