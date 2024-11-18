package model;

import static utils.MyUtils.shuffleArray;

public class Question {
    private String body;
    private String[] answers;
    private int correctAnswer;
    private int number;
    private int countOfAnswers;
    private String explanation; // в процессе

    public void shuffleAnswers() {
        String rightAnswer = getAnswers()[getCorrectAnswer() - 1];

        shuffleArray(getAnswers());

        for (int i = 0; i < getCountOfAnswers(); i++)
            if (getAnswers()[i].equals(rightAnswer)) {
                setCorrectAnswer(i + 1);
                break;
            }
    }

    public String getBody() {
        return body;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
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

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}



