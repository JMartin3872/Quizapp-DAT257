package com.example;
import java.util.*;

public class ModelTrueFalse {
    private Map<Integer, Question> questionMap = new HashMap<>();
    private int currentQuestionId = 0; // Default to the first question
    private String userName;
    private int score = 0;
    private int correctAnswers = 0;
    private int wrongAnswers = 0;
    private int nrQuestions;

    private String trueOrFalseInfo = "In this quiz you are tasked with selecting whether the statement is true or false.";


    public ModelTrueFalse() {
        // QuestionReaderMultipleChoices used to read questions from  testQeustions.txt
        QuestionReaderTrueFalse reader = new QuestionReaderTrueFalse();
        LinkedList<Question> questions = reader.readQuestionFile("True-False-Questions.txt");
        
        for (Question question : questions) {
            questionMap.put(question.getQuestionId(), question);
        }
        trueOrFalseInfo += "\n\n There are " + getTotalQuestions() + " questions in this quiz.";
    }

    public Question getQuestion(int questionId) {
        // returns null if question doesn't exist
        return questionMap.get(questionId);
    }

    // Fetch the current question
    public Question getCurrentQuestion() {
        return questionMap.get(currentQuestionId);
    }

    // Get current question ID
    public int getCurrentQuestionId() {
        return getCurrentQuestion().getQuestionId();
    }

    // Get current question text
    public String getCurrentQuestionText() {
        return getCurrentQuestion().getQuestionText();
    }

    public String getQuestionText(int questionId) {
        return getQuestion(questionId).getQuestionText();
    }

    // Get the answer alternatives of the current question
    public ArrayList<String> getCurrentQuestionAnswers() {
        return getCurrentQuestion().getAnswers();
    }

    // Fetch trivia for the current question
    public String getCurrentQuestionTrivia() {
        return questionMap.get(currentQuestionId).getTrivia();
    }

    // Fetch trivia for the current question
    public String getQuestionTrivia(int questionId) {
        Question q = getQuestion(questionId);
        if (q instanceof QuestionMultipleChoices) {
            return ((QuestionMultipleChoices) q).getTrivia();
        }
        return "";
    }

    // Get the correct answer for the current question
    public String getCurrentQuestionCorrectAnswer() {
        return questionMap.get(currentQuestionId).getCorrectAnswer();
    }

    public String getQuestionCorrectAnswer(int questionId) {
        return questionMap.get(questionId).getCorrectAnswer();
    }

    public boolean checkAnswer(String userAnswer) {
        return getCurrentQuestionCorrectAnswer().equals(userAnswer);
    }

    // Proceed to the next question
    public void nextQuestion() {
        if (questionMap.containsKey(currentQuestionId + 1)) {
            currentQuestionId++;
        }
    }

    // Returns the total amount of questions in the quiz
    public int getTotalQuestions() {
        return questionMap.size();
    }

    // Checks whether the current quiz is finished
    public boolean isFinished() {
        return currentQuestionId >= questionMap.size() - 1;
    }

    // Gets info about True or False quiz
    public String getTFQuizInfo() {
        return trueOrFalseInfo;
    }

    // Sets the username
    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return this.userName;
    }

    public void restartQuiz(){
        this.currentQuestionId = 0;
    }

    // For now, these functions reside in the model. Will be moved to User class later
    public void correctAnswer() {
        this.correctAnswers++;
        score++;
    }

    public void wrongAnswer() {
        this.wrongAnswers++;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public int getScore() {
        return score;
    }
}
