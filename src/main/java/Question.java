public class Question {
    public String body = "";
    public String[] answers;
    int correctAnswer;
    int number;
    int countOfAnswers;
    public Question() {}

    Question(String body, String[] answers, int correctAns, int number, int countOfAnswers) {
        this.body = body;
        this.answers = answers;
        this.correctAnswer = correctAns;
        this.number = number;
        this.countOfAnswers = countOfAnswers;
    }
}



