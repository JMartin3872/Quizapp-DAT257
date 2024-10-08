package com.example;

import java.util.*;

public class QuizGameModel {
    private Map<Integer, Question> questionMap = new HashMap<>();
    private int currentQuestionId = 0; // Default to the first question
    private String userName;
    private int score = 0;
    private int correctAnswers = 0;
    private int wrongAnswers = 0;

    private String multipleChoiceInfo = "This quiz presents you with 15 questions and several answer alternatives.\n\n"
        + "You are tasked with selecting the correct amount of carbon dioxide equivalents (kg) the following emit.\n\n"
        + "Select your answer and press \"Submit Answer\".";

    public QuizGameModel() {
        // QuestionReaderMultipleChoices used to read questions from  testQeustions.txt
        QuestionReaderMultipleChoices reader = new QuestionReaderMultipleChoices();
        LinkedList<Question> questions = reader.readQuestionFile("Multiplechoices-Questions.txt");

        for (Question question : questions) {
            questionMap.put(question.getQuestionId(), question);
        }
    }

    // Obsolete, maybe remove?
    // Constructor with list of questions
    public QuizGameModel(List<Question> questions) {
        for (Question question : questions) {
            questionMap.put(question.getQuestionId(), question);
        }
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

    public String getQuestionText(int questionId) {
        return getQuestion(questionId).getQuestionText();
    }

    // Get current question text
    public String getCurrentQuestionText() {
        return getCurrentQuestion().getQuestionText();
    }

    // Get the answer alternatives of the current question
    public ArrayList<String> getCurrentQuestionAnswers() {
        return getCurrentQuestion().getAnswers();
    }

    // Fetch trivia for the current question
    public String getQuestionTrivia(int questionId) {
        Question q = getQuestion(questionId);
        if (q instanceof QuestionMultipleChoices) {
            return ((QuestionMultipleChoices) q).getTrivia();
        }
        return "";
    }
    // Fetch trivia for the current question
    public String getCurrentQuestionTrivia() {
        Question currentQuestion = getCurrentQuestion();
        if (currentQuestion instanceof QuestionMultipleChoices) {
            return ((QuestionMultipleChoices) currentQuestion).getTrivia();
        }
        return "";
    }

    public String getQuestionCorrectAnswer(int questionId) {
        Question q = getQuestion(questionId);
        if (q instanceof QuestionMultipleChoices) {
            return ((QuestionMultipleChoices) q).getCorrectAnswer();
        }
        return "";
    }

    // Get the correct answer for the current question
    public String getCurrentQuestionCorrectAnswer() {
        Question currentQuestion = getCurrentQuestion();
        if (currentQuestion instanceof QuestionMultipleChoices) {
            return ((QuestionMultipleChoices) currentQuestion).getCorrectAnswer();
        }
        return "";
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

    // Gets info about Multiple Choice quiz
    public String getMCQuizInfo() {
        return multipleChoiceInfo;
    }

    // Sets the username
    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return this.userName;
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

