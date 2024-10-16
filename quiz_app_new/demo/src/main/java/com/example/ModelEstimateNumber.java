package com.example;
import java.util.*;

public class ModelEstimateNumber {
    private Map<Integer, Question> questionMap = new HashMap<>();
    private int currentQuestionId = 0; // Default to the first question
    private String userName;
    private int score = 0;
    private int correctAnswers = 0;
    private int wrongAnswers = 0;

    private String estimateNumberInfo = "In this quiz your task is to answer the question with a number estimate and write it.\n\n"
        + "The answer is correct if it is within the margins of -+ 10% of the exact answer.";


    public ModelEstimateNumber() {
        // QuestionReaderEstimateNumber used to read questions from txt-file
        QuestionReaderEstimateNumber reader = new QuestionReaderEstimateNumber();
        LinkedList<Question> questions = reader.readQuestionFile("EstimateQuiz-Questions.txt");

        for (Question question : questions) {
            questionMap.put(question.getQuestionId(), question);
        }
        estimateNumberInfo += "\n\n There are " + getTotalQuestions() + " questions in this quiz.";
    }

    // Fetch the current question
    public Question getCurrentQuestion() {
        return questionMap.get(currentQuestionId);
    }

    // Get question based on ID
    public Question getQuestion(int questionId) {
        return questionMap.get(questionId);
    }

    // Get current question ID
    public int getCurrentQuestionId() {
        return getCurrentQuestion().getQuestionId();
    }

    // Get current question text
    public String getCurrentQuestionText() {
        return getCurrentQuestion().getQuestionText();
    }

    // Get question text based on ID
    public String getQuestionText(int questionId) {
        return getQuestion(questionId).getQuestionText();
    }

    // Fetch trivia for the current question
    public String getCurrentQuestionTrivia() {
        return questionMap.get(currentQuestionId).getTrivia();
    }

    // Get the correct answer for the current question
    public String getCurrentQuestionCorrectAnswer() {
        return questionMap.get(currentQuestionId).getCorrectAnswer();
    }

    // Get the correct answer for the question based on ID
    public String getQuestionCorrectAnswer(int questionId) {
        return questionMap.get(questionId).getCorrectAnswer();
    }

    //checks whether or not the useranswer is within -+ 10% of the correct answer,
    //assumes that useranswer is a number, returns true if useranswer is within -+10%
    public boolean checkAnswer(String userAnswer) {
        int correctAnswerInt = Integer.parseInt(this.getCurrentQuestionCorrectAnswer());
        int userAnswerInt = 0;

        try { 
            userAnswerInt = Integer.parseInt(userAnswer); 
        } catch(NumberFormatException e) { 
            return false; 
        } catch(NullPointerException e) {
            return false;
        }

        double lowerNumber = (double)(0.9 * correctAnswerInt);
        double higherNumber = (double)(1.1 * correctAnswerInt);

        int lowerNumberInt = (int)Math.round(lowerNumber);
        int higherNumberInt = (int)Math.round(higherNumber);

        if(userAnswerInt >= lowerNumberInt && userAnswerInt <= higherNumberInt){
            return true;
        }
        else{
            return false;
        }
    }

    // Proceed to the next question
    public void nextQuestion() {
        if (questionMap.containsKey(currentQuestionId + 1)) {
            currentQuestionId++;
        }
        else {
            currentQuestionId = getTotalQuestions();
        }
    }

    // Returns the total amount of questions in the quiz
    public int getTotalQuestions() {
        return questionMap.size();
    }

    // Checks whether the current quiz is finished
    public boolean isFinished() {
        return currentQuestionId >= questionMap.size();
    }

    // Gets info about True or False quiz
    public String getEstimateNumberQuizInfo() {
        return estimateNumberInfo;
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

    public int getScore() {
        return score;
    }
}
