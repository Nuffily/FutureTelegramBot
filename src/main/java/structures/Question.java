package structures;

public class Question {
    public String body = "";
    public String[] answers;
    public int correctAnswer;
    public   int number;
    public   int countOfAnswers;

    public Question() {}

    public Question(String body, String[] answers, int correctAns, int number, int countOfAnswers) {
        this.body = body;
        this.answers = answers;
        this.correctAnswer = correctAns;
        this.number = number;
        this.countOfAnswers = countOfAnswers;
    }
}



